package net.snowlynxsoftware.authservice.managers;

import net.snowlynxsoftware.authservice.AppConstants;
import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.dtos.AuthorizedDto;
import net.snowlynxsoftware.authservice.dtos.UserLoginRequestDto;
import net.snowlynxsoftware.authservice.dtos.UserRegisterRequestDto;
import net.snowlynxsoftware.authservice.dtos.UserVerifyRequestDto;
import net.snowlynxsoftware.authservice.email_templates.EmailTemplates;
import net.snowlynxsoftware.authservice.entities.AuthTokenEntity;
import net.snowlynxsoftware.authservice.entities.UserEntity;
import net.snowlynxsoftware.authservice.models.TokenType;
import net.snowlynxsoftware.authservice.repositories.AuthTokenRepository;
import net.snowlynxsoftware.authservice.repositories.UserRepository;
import net.snowlynxsoftware.authservice.services.EmailService;
import net.snowlynxsoftware.authservice.services.HashService;
import net.snowlynxsoftware.authservice.services.RandomNumberService;
import net.snowlynxsoftware.authservice.services.TokenService;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles operations involving Users.
 */
public class UserManager {

    /**
     * Creates a new user--returns true if successful.
     *
     * @param registerRequestDto
     * @param userRepository
     * @return
     */
    public static boolean createNewUser(UserRegisterRequestDto registerRequestDto, UserRepository userRepository, AuthConfigProperties config) {

        // First let's check if there is already a user with this email address registered.
        UserEntity existing = userRepository.findByEmail(registerRequestDto.getEmail());
        if (existing != null) {
            return false;
        }

        UserEntity entity = new UserEntity(registerRequestDto);
        UserEntity saved;

        // Ensure hashing worked
        if (entity.getHash() != null) {
            saved = userRepository.save(entity);

            // Ensure saving returned an ID
            if (saved.getId() != 0) {

                String emailTemplate = EmailTemplates.newUserCreateSuccessTemplate(saved, config);

                try {
                    EmailService.sendEmail(config.getSendgridFromEmail(), "Welcome to Why I Love You!", saved.getEmail(), emailTemplate, config);
                } catch (IOException ex) {
                    // If the email fails to send--we need to delete the user so they can try again!
                    System.out.printf(ex.getMessage());
                    userRepository.delete(saved);
                    return false;
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Verifies the user account.
     *
     * @param verifyRequestDto
     * @param userRepository
     * @return
     */
    public static boolean verifyNewUser(UserVerifyRequestDto verifyRequestDto, UserRepository userRepository, AuthConfigProperties config) {

        // Ensure all fields were sent.
        if (verifyRequestDto.getEmail() == null || verifyRequestDto.getPassword() == null || verifyRequestDto.getAuthCode() == 0) {
            return false;
        }

        // Find the user requesting verification.
        UserEntity existing = getUserByEmail(verifyRequestDto.getEmail(), userRepository);

        if (existing == null) {
            return false;
        } else if (existing.isVerified()) {
            return false;
        }

        boolean isAuthentic = false;

        try {
            isAuthentic = HashService.isAuthentic(verifyRequestDto.getPassword(), existing.getHash());
        } catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }

        // Ensure the password is correct
        if (!isAuthentic) {
            return false;
        }

        // Check that the authCode matches
        if (existing.getAuthCode() != verifyRequestDto.getAuthCode()) {
            return false;
        }

        // If we got this far--the user should be verified.
        try {
            existing.setVerified(true);
            existing.setLastEditDate(new Date());
            existing.setAuthCode(ThreadLocalRandom.current().nextInt(AppConstants.AUTH_CODE_MIN, AppConstants.AUTH_CODE_MAX));
            userRepository.save(existing);

            // Send an email to the user for the new authCode
            try {
                EmailService.sendEmail(config.getSendgridFromEmail(), "Account Verified!", existing.getEmail(), EmailTemplates.newUserVerifySuccessTemplate(existing, config), config);
            } catch(IOException ex) {
                System.out.printf(ex.getMessage());
            }

            return true;
        } catch (Exception ex) {
            System.out.printf(ex.getMessage());
            return false;
        }
    }

    /**
     * Attempts to login the user.
     *
     * @param loginRequestDto
     * @param userRepository
     * @return
     */
    public static AuthorizedDto loginUser(UserLoginRequestDto loginRequestDto, UserRepository userRepository, AuthTokenRepository authTokenRepository, String ipAddress, AuthConfigProperties config) {

        // First ensure all required info was passed in.
        if (loginRequestDto.getEmail() == null || loginRequestDto.getPassword() == null) {
            return null;
        }

        // Find the user requesting verification.
        UserEntity existing = getUserByEmail(loginRequestDto.getEmail(), userRepository);

        if (existing == null) {
            return null;
        } else if (!existing.isVerified()) {
            return null;
        }

        boolean isAuthentic = false;

        try {
            isAuthentic = HashService.isAuthentic(loginRequestDto.getPassword(), existing.getHash());
        } catch (Exception ex) {
            System.out.printf(ex.getMessage());
        }

        // Ensure the password is correct
        if (!isAuthentic) {
            return null;
        }

        // If we got this far--the user should be verified--generate the new tokens.
        try {
            AuthTokenEntity tokenEntity = new AuthTokenEntity();
            tokenEntity.setAuthToken(TokenService.generateNewToken(existing.getEmail(), ipAddress, TokenType.AUTH_TOKEN, config));
            tokenEntity.setRefreshToken(TokenService.generateNewToken(existing.getEmail(), ipAddress, TokenType.REFRESH_TOKEN, config));
            tokenEntity.setSecret(config.getJwtSecretKey());
            tokenEntity.setId(UUID.randomUUID());

            AuthTokenEntity saved = authTokenRepository.save(tokenEntity);

            if (saved != null) {
                return new AuthorizedDto(saved);
            }

        } catch (Exception ex) {
            System.out.printf(ex.getMessage());
            return null;
        }

        return null;

    }

    /**
     * Given an auth token, finds it in the database--and deletes it--effectively invalidating all future requests that will rely on it.
     *
     * @param authToken
     * @param authTokenRepository
     * @return
     */
    public static boolean logoutUser(String authToken, AuthTokenRepository authTokenRepository) {

        AuthTokenEntity existing = authTokenRepository.findByAuthToken(authToken);

        if (existing != null) {
            authTokenRepository.delete(existing);
            return true;
        }

        return false;
    }

    /**
     * Returns a user Entity associated with the given email address.
     * @param email
     * @param userRepository
     * @return
     */
    public static UserEntity getUserByEmail(String email, UserRepository userRepository) {
        return userRepository.findByEmail(email.toUpperCase());
    }

    /**
     * Will reset the authCode for a user--then send an email with the new code for verification.
     * @param email
     * @param userRepository
     * @return
     */
    public static boolean forgotPasswordRequest(String email, UserRepository userRepository, AuthConfigProperties config) {

        UserEntity existing = getUserByEmail(email, userRepository);

        if (existing == null) {
            return false;
        }

        // Reset the authCode for this user
        int authCode = RandomNumberService.generateRandomInteger(AppConstants.AUTH_CODE_MIN, AppConstants.AUTH_CODE_MAX);
        existing.setAuthCode(authCode);
        existing.setLastEditDate(new Date());
        userRepository.save(existing);

        // Send an email to the user for the new authCode
        try {
            EmailService.sendEmail(config.getSendgridFromEmail(), "Password Reset Request", existing.getEmail(), EmailTemplates.passwordResetRequestTemplate(existing, config), config);
            return true;
        } catch(IOException ex) {
            System.out.printf(ex.getMessage());
            return false;
        }
    }

    public static boolean changeUserPassword(UserVerifyRequestDto request, UserRepository userRepository, AuthConfigProperties config) {

        UserEntity existing = getUserByEmail(request.getEmail(), userRepository);

        if (existing == null) {
            return false;
        }

        // Ensure the authCodes match
        if (existing.getAuthCode() != request.getAuthCode()) {
            return false;
        } else {
            // Change the users password
            UserEntity toSave = new UserEntity(existing, request.getPassword());
            userRepository.save(toSave);

            return true;
        }
    }

}

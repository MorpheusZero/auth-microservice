package net.snowlynxsoftware.authservice.email_templates;

import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.entities.UserEntity;

/**
 * Various string templates for emails.
 */
public class EmailTemplates {

    /**
     * Template for after a new user is created and verification is required.
     * @param user
     * @return
     */
    public static String newUserCreateSuccessTemplate(UserEntity user, AuthConfigProperties config) {
        return "Hello " + user.getEmail() + "! Welcome to " + config.getAppDisplayName() + "! There is still one last step to activating your account. Please click this link: " + config.getAppVerifyRedirect().replace("{0}", user.getEmail()).replace("{1}", String.valueOf(user.getAuthCode())) + " to verify your account.";
    }

    /**
     * Template for after a new user account has been verified.
     * @param user
     * @return
     */
    public static String newUserVerifySuccessTemplate(UserEntity user, AuthConfigProperties config) {
        return "Welcome to " + config.getAppDisplayName() + " " + user.getEmail() + "! Your account has been verified!";
    }

    /**
     * Template for when a user has requested a password reset.
     * @param user
     * @return
     */
    public static String passwordResetRequestTemplate(UserEntity user, AuthConfigProperties config) {
        return  user.getEmail() + ", a password reset request has been initiated for your account. Please go to " + config.getAppChangePasswordRedirect().replace("{0}", user.getEmail()).replace("{1}", String.valueOf(user.getAuthCode())) + " to change your password. If you did not request a password change--you can disregard this email.";
    }

}

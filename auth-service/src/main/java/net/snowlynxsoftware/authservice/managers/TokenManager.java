package net.snowlynxsoftware.authservice.managers;

import net.snowlynxsoftware.authservice.config.AuthConfigProperties;
import net.snowlynxsoftware.authservice.dtos.AuthorizedDto;
import net.snowlynxsoftware.authservice.entities.AuthTokenEntity;
import net.snowlynxsoftware.authservice.models.TokenType;
import net.snowlynxsoftware.authservice.repositories.AuthTokenRepository;
import net.snowlynxsoftware.authservice.services.TokenService;

import java.util.UUID;

/**
 * Handles operations involving Tokens.
 */
public class TokenManager {

    public static AuthorizedDto validateToken(String authToken, String refreshToken, String ipAddress, AuthTokenRepository authTokenRepository, AuthConfigProperties config) {

        // First attempt to validate the authToken
        boolean isAuthTokenValid = TokenService.isTokenAuthentic(authToken, ipAddress, config);

        // If authToken is invalid--we need to attempt validation of the refreshToken
        if (!isAuthTokenValid) {

            boolean isRefreshTokenValid = TokenService.isTokenAuthentic(refreshToken, ipAddress, config);

            // If refreshToken is valid--generate a new token, otherwise--return null--the request is unauthorized.
            if (!isRefreshTokenValid) {
                return null;
            } else {
                // generate a new authToken--
                String email = TokenService.getTokenSubject(refreshToken, config);

                if (email == null) {
                    return null;
                }

                AuthTokenEntity entity = new AuthTokenEntity();
                entity.setId(UUID.randomUUID());
                entity.setSecret(config.getJwtSecretKey());
                entity.setAuthToken(TokenService.generateNewToken(email, ipAddress, TokenType.AUTH_TOKEN, config));
                entity.setRefreshToken(refreshToken);
                authTokenRepository.save(entity);

                // Delete the old one
                AuthTokenEntity old = authTokenRepository.findByAuthToken(authToken);

                if (old != null) {
                    authTokenRepository.delete(old);
                }

                // return the authorizedDto
                return new AuthorizedDto(entity);

            }

        } else {

            AuthTokenEntity existing = authTokenRepository.findByAuthToken(authToken);

            return existing != null ? new AuthorizedDto(existing) : null;

        }

    }

}

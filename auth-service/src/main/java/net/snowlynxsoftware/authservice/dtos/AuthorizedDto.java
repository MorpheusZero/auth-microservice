package net.snowlynxsoftware.authservice.dtos;

import net.snowlynxsoftware.authservice.entities.AuthTokenEntity;

/**
 * Returned to the client when a user has been authenticated.
 * Contains the authToken for authenticated requests--
 * And the refreshToken for generating a new authToken if necessary.
 */
public class AuthorizedDto {

    private String authorizationToken;

    private String refreshToken;

    public AuthorizedDto()
    {
    }

    public AuthorizedDto(AuthTokenEntity tokenEntity)
    {
        authorizationToken = tokenEntity.getAuthToken();
        refreshToken = tokenEntity.getRefreshToken();
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

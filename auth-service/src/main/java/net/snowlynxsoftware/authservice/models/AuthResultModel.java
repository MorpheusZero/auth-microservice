package net.snowlynxsoftware.authservice.models;

import net.snowlynxsoftware.authservice.entities.UserEntity;

/**
 * Represents the result of attempting to authenticate a user.
 * Returns the authenticated UserEntity and the associated AuthToken.
 */
public class AuthResultModel {

    private UserEntity authenticatedUser;
    private String authToken;

    public AuthResultModel()
    {
    }

    public UserEntity getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(UserEntity authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

package net.snowlynxsoftware.authservice.entities;

import javax.persistence.*;
import java.util.UUID;

/**
 * Represents authTokens for authenticated users.
 */
@Entity
@Table(name = "AuthTokens")
public class AuthTokenEntity {

    @Id
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "AuthToken")
    private String authToken;

    @Column(name = "RefreshToken")
    private String refreshToken;

    @Column(name = "Secret")
    private String secret;

    public AuthTokenEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

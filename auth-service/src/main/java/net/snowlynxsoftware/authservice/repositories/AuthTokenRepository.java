package net.snowlynxsoftware.authservice.repositories;

import net.snowlynxsoftware.authservice.entities.AuthTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Used to set the context of the authToken table for all AuthToken.
 */
public interface AuthTokenRepository extends JpaRepository<AuthTokenEntity, UUID> {
    AuthTokenEntity findByAuthToken(String authToken);
}
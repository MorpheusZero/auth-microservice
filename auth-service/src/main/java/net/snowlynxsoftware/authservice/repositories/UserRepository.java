package net.snowlynxsoftware.authservice.repositories;

import net.snowlynxsoftware.authservice.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Used to set the context of the User table for all userEntities.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
}

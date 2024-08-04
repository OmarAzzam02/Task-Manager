package org.eastnets.dao;

import org.eastnets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Access Object (DAO) interface for performing CRUD operations on {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations and custom queries
 * for {@link User} entities. It includes methods for user authentication and retrieval by username.
 */
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their username and password.
     * <p>
     * This method is used for authentication purposes, verifying that a user exists with the given credentials.
     *
     * @param enteredUsername the username of the user.
     * @param enteredPassword the password of the user.
     * @return the user with the specified username and password, or {@code null} if no such user exists.
     */
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    User login(@Param("username") String enteredUsername, @Param("password") String enteredPassword);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user.
     * @return an {@link Optional} containing the user with the specified username, or {@code Optional.empty()} if no such user exists.
     */
    User findByUsername(String username);
}

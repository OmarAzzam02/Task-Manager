package org.eastnets.service.user;

import lombok.extern.log4j.Log4j2;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;
import org.eastnets.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface for managing user-related operations.
 * <p>
 * This service provides methods for signing in users, updating user details, signing up new users,
 * updating user privileges, and retrieving all users. It uses a {@link UserDAO} for data access.
 * </p>
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO db;

    /**
     * Signs in a user by verifying their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the {@link User} object if the credentials are valid; otherwise, returns {@code null}
     */
    @Override
    public User signin(String username, String password) {
        try {
            log.info("Attempting to sign in user: {}", username);
            User user = db.login(username, password);
            if (user != null) {
                log.info("User found, returning user");
            } else {
                log.info("User not found");
            }
            return user;
        } catch (Exception e) {
            log.error("Error during sign-in: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Updates the details of an existing user.
     *
     * @param user the {@link User} object containing updated details
     */
    @Override
    public void updateUser(User user) {
        try {
            log.info("Attempting to update user with ID: {}", user.getUserId());
            db.save(user);
            log.info("User updated successfully");
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
        }
    }

    /**
     * Registers a new user in the system.
     *
     * @param user the {@link User} object to be registered
     */
    @Override
    public void signup(User user) {
        try {
            log.info("Attempting to sign up user: {}", user.getUsername());
            if (db.findById(user.getUserId()).isPresent()) {
                throw new Exception("User already exists");
            }
            db.save(user);
            log.info("User signed up successfully: {}", user.getUsername());
        } catch (Exception ex) {
            log.error("Error signing up user: {}", ex.getMessage());
        }
    }

    /**
     * Updates the privileges of an existing user.
     *
     * @param user the {@link User} object whose privileges are to be updated
     * @param role the {@link UserType} representing the role of the user updating the previlage
     */
    @Override
    public void updatePrivilege(User user, UserType role) {
        try {
            log.info("Attempting to update privileges for user: {}", user.getUsername());
            if (!role.hasUpdatePrivlage()) {
                throw new Exception("User does not have update privilege");
            }
            db.save(user);
            log.info("User privileges updated successfully");
        } catch (Exception e) {
            log.error("Error updating user privileges: {}", e.getMessage());
        }
    }

    /**
     * Retrieves a list of all users based on the provided user type.
     *
     * @param userType the {@link UserType} used to determine access privileges
     * @return a list of all {@link User} objects if the user has appropriate privileges;
     *         otherwise, returns {@code null}
     */
    @Override
    public List<User> getAllUsers(UserType userType) {
        try {
            log.info("Attempting to retrieve all users");
            if (!userType.hasViewAllTasksAndUsersPrivlage()) {
                log.error("User does not have privilege to view all users");
                throw new Exception("You don't have this privilege");
            }
            return db.findAll();
        } catch (Exception ex) {
            log.error("Error retrieving users: {}", ex.getMessage());
            return null;
        }
    }
}

package org.eastnets.service.user;

import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

/**
 * Service interface for managing users.
 * <p>
 * This interface defines methods for performing operations related to users,
 * including signing in, signing up, updating user details, updating user
 * privileges, and retrieving all users based on user type.
 * </p>
 */
public interface UserService {

    /**
     * Signs in a user by verifying their username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the {@link User} object if the credentials are valid
     */
    User signin(String username, String password);

    /**
     * Updates an existing user's details.
     *
     * @param user the user object containing updated details
     */
    void updateUser(User user);

    /**
     * Registers a new user in the system.
     *
     * @param user the user object to be registered
     */
    void signup(User user);

    /**
     * Updates the privileges of an existing user.
     *
     * @param user the user whose privileges are to be updated
     * @param role the new role to be assigned to the user
     */
    void updatePrivilege(User user, UserType role);

    /**
     * Retrieves a list of all users based on the specified user type.
     *
     * @param userType the type of user requesting the user list
     * @return a list of all users if the user has appropriate privileges, otherwise an empty list
     */
    List<User> getAllUsers(UserType userType);
}

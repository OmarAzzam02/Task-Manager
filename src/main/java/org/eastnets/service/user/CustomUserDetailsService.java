package org.eastnets.service.user;

import org.eastnets.dao.UserDAO;
import org.eastnets.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Custom implementation of {@link UserDetailsService} for loading user-specific data.
 * <p>
 * This service is responsible for retrieving user details from the database
 * based on the username provided. It implements Spring Security's
 * {@link UserDetailsService} interface to integrate with the authentication process.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to be retrieved
     * @return a {@link UserDetails} object containing the user's details
     * @throws UsernameNotFoundException if the user with the specified username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}

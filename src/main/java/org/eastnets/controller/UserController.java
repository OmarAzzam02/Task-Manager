package org.eastnets.controller;

import lombok.extern.log4j.Log4j2;
import org.eastnets.dto.user.SigninDTO;
import org.eastnets.dto.user.UserInfoDTO;
import org.eastnets.dto.user.UserPrivilegeUpdateDTO;
import org.eastnets.entity.User;
import org.eastnets.service.user.UserService;
import org.eastnets.securityutil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling user-related operations.
 * <p>
 * This controller provides endpoints for user signup, login, listing users, updating user details,
 * and updating user privileges.
 * </p>
 */
@RestController
@RequestMapping("")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint to get a welcome message.
     * <p>
     * Returns a welcome message for the home page.
     * </p>
     *
     * @return a welcome message
     */
    @GetMapping
    public String home() {
        return "Welcome to the home page!";
    }

    /**
     * Endpoint to register a new user.
     * <p>
     * Accepts a {@link UserInfoDTO} containing user information, creates a new {@link User}, and registers it.
     * </p>
     *
     * @param tempUser the {@link UserInfoDTO} containing user information
     * @return a {@link ResponseEntity} with a success or error message
     */
    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> signup(@RequestBody UserInfoDTO tempUser) {
        try {
            User user = new User(tempUser.getUsername(), tempUser.getPassword(), tempUser.getEmail(), tempUser.getUserType());
            log.info("Received signup request: {}", user);
            userService.signup(user);
            log.info("User signed up successfully: {}", user.getUsername());
            return ResponseEntity.ok().body("User signed up successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    /**
     * Endpoint for user login.
     * <p>
     * Accepts {@link SigninDTO} with username and password, authenticates the user, and returns a JWT token if successful.
     * </p>
     *
     * @param credentials the {@link SigninDTO} containing username and password
     * @return a {@link ResponseEntity} with a JWT token or an error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody SigninDTO credentials) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        return ResponseEntity.ok().body(jwtUtil.generateToken(credentials.getUsername()));
    }

    /**
     * Endpoint to get a list of users.
     * <p>
     * Accepts a {@link UserInfoDTO} containing user information and returns a list of users based on the user type.
     * </p>
     *
     * @param user the {@link UserInfoDTO} containing user information
     * @return a {@link ResponseEntity} with a list of users or an error message
     */
    @PostMapping("/users-list")
    public ResponseEntity<?> UsersList(@RequestBody UserInfoDTO user) {
        List<User> users = userService.getAllUsers(user.getUserType());

        if (users != null)
            return ResponseEntity.ok().body("Users found");

        return ResponseEntity.badRequest().body("No users found");
    }

    /**
     * Endpoint to update user details.
     * <p>
     * Accepts a {@link UserInfoDTO} containing updated user information and updates the user details.
     * </p>
     *
     * @param tempUser the {@link UserInfoDTO} containing updated user information
     * @return a {@link ResponseEntity} with a success or error message
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserInfoDTO tempUser) {
        try {
            User user = new User(tempUser.getUserId(), tempUser.getUsername(), tempUser.getPassword(), tempUser.getEmail(), tempUser.getUserType());
            userService.updateUser(user);
            return ResponseEntity.ok().body("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred; could not update user");
        }
    }

    /**
     * Endpoint to update user privileges.
     * <p>
     * Accepts a {@link UserPrivilegeUpdateDTO} containing user information for both the user to update and the user updating the privilege,
     * and updates the privileges of the user to update.
     * </p>
     *
     * @param tempUser the {@link UserPrivilegeUpdateDTO} containing user privilege information
     * @return a {@link ResponseEntity} with a success or error message
     */
    @PostMapping("/update-privlage")
    public ResponseEntity<?> updateUserPrivilege(@RequestBody UserPrivilegeUpdateDTO tempUser) {
        try {
            User userToUpdate = new User(tempUser.getToUpdate().getUserId(), tempUser.getToUpdate().getUsername(), tempUser.getToUpdate().getPassword(), tempUser.getToUpdate().getEmail(), tempUser.getToUpdate().getUserType());
            User userUpdatedBy = new User(tempUser.getUpdatedBy().getUserId(), tempUser.getUpdatedBy().getUsername(), tempUser.getUpdatedBy().getPassword(), tempUser.getUpdatedBy().getEmail(), tempUser.getUpdatedBy().getUserType());
            userService.updatePrivilege(userToUpdate, userUpdatedBy.getUserType());
            return ResponseEntity.ok().body("User privilege updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred; could not update user privileges");
        }
    }
}

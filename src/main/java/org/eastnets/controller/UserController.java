package org.eastnets.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.dto.SigninDTO;
import org.eastnets.dto.UserDTO;
import org.eastnets.entity.User;
import org.eastnets.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static Logger logger = LogManager.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }


    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> signup(@RequestBody UserDTO tempUser) {
        try {
            User user = new User(tempUser.getUsername() , tempUser.getPassword() , tempUser.getEmail() , tempUser.getUserType());
            logger.info("Received signup request: {}", user);
            userService.signup(user);
            logger.info("User signed up successfully: {}", user.getUsername());
            return ResponseEntity.ok().body("User signed up successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


        @PostMapping("/signin")
        ResponseEntity<?> signin(@RequestBody SigninDTO credentials) {
            User user =   userService.signin(credentials.getUsername(), credentials.getPassword());

            if(user != null){
             logger.info("User logged in successfully {} " , user.getUsername()  , user.getTasksAssigned() );
             return ResponseEntity.ok().body(user);
            }

            return ResponseEntity.badRequest().body("Invalid username or password");

        }


    @PostMapping("/home/users-list")
    ResponseEntity<?> UsersList(@RequestBody UserDTO user) {
        List<User> users= userService.getAllUsers(user.getUserType());
        if(users != null)
            return ResponseEntity.ok().body(users);

        return ResponseEntity.badRequest().body("No users found");

    }










}



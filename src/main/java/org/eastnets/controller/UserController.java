package org.eastnets.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.dto.user.SigninDTO;
import org.eastnets.dto.user.UserManipulationDTO;
import org.eastnets.dto.user.UserPrivilegeUpdateDTO;
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

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page!";
    }


    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> signup(@RequestBody UserManipulationDTO tempUser) {
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
             return ResponseEntity.ok().body("Sign in Successfully ");
            }

            return ResponseEntity.badRequest().body("Invalid username or password");

        }


    @PostMapping("/users-list")
    ResponseEntity<?> UsersList(@RequestBody UserManipulationDTO user) {
        List<User> users= userService.getAllUsers(user.getUserType());
        if(users != null)
            return ResponseEntity.ok().body(users);

        return ResponseEntity.badRequest().body("No users found");

    }

    @PostMapping("/update")
    ResponseEntity<?> updateUser(@RequestBody UserManipulationDTO tempUser) {
        try {
        User user = new User(tempUser.getUserId() , tempUser.getUsername() , tempUser.getPassword(),tempUser.getEmail() ,tempUser.getUserType() );
         userService.updateUser(user);

        return ResponseEntity.ok().body("User updated successfully");
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred could not update user");

        }

    }    @PostMapping("/update-privlage")
    ResponseEntity<?> updateUserPrivilege(@RequestBody UserPrivilegeUpdateDTO tempUser) {
        try {
        User userToUpdate = new User(tempUser.getToUpdate().getUserId() , tempUser.getToUpdate().getUsername() , tempUser.getToUpdate().getPassword(),tempUser.getToUpdate().getEmail() ,tempUser.getToUpdate().getUserType() );
        User userUpdateBy = new User(tempUser.getUpdatedBy().getUserId(), tempUser.getUpdatedBy().getUsername() , tempUser.getUpdatedBy().getPassword(),tempUser.getUpdatedBy().getEmail() ,tempUser.getUpdatedBy().getUserType() );
         userService.updatePrivilege(userToUpdate , userUpdateBy.getUserType());

        return ResponseEntity.ok().body("User updated successfully");
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred could not update user");

        }

    }










}



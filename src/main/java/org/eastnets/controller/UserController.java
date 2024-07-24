package org.eastnets.controller;


import org.eastnets.entity.User;
import org.eastnets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-manager")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }


    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestParam User user) {
        try {
        userService.signup(user);
        return ResponseEntity.ok().build();

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
      User user =   userService.signin(username, password);
      if(user != null)
          return ResponseEntity.ok().body(user);

      return ResponseEntity.badRequest().body("Invalid username or password");

    }


    @PostMapping("/home/users-list")
    ResponseEntity<?> UsersList(@RequestParam User user) {
        List<User> users= userService.getAllUsers(user.getUserType());

        if(users != null)
            return ResponseEntity.ok().body(users);

        return ResponseEntity.badRequest().body("No users found");

    }










}

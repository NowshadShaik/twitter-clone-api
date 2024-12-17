package com.twitter.backend.controller;

import com.twitter.backend.modals.User;
import com.twitter.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/twitter")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        String login = userService.login(user);
        if(!login.equals("Login Failed")) {
            return new ResponseEntity<>(login, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Login failed for user: " + user.getUsername(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        User registeredUser = null;
        try {
            registeredUser = userService.createUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>("Username already exists: " + user.getUsername(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/users/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody User user) {

        User savedUser = userService.deleteUser(user);

        if(savedUser == null) {
            return new ResponseEntity<>("Invalid user name: " + user.getUsername() + " does not exist", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/listUsers")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.ACCEPTED);
    }
}

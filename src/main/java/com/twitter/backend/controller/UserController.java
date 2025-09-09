package com.twitter.backend.controller;

import com.twitter.backend.modals.User;
import com.twitter.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String login = userService.login(user);
        if(!login.equals("Login Failed")) {
            return new ResponseEntity<>(login, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Login failed for user: " + user.getUsername(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = null;
        try {
            registeredUser = userService.createUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>("Username already exists: " + user.getUsername(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("user") String username) {

        if(userService.deleteUser(username)) {
            return new ResponseEntity<>(String.format("User with username: %s is deleted successfully", username), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Invalid user name: " + username + " or You are not the owner of this account", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<String>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.ACCEPTED);
    }
}

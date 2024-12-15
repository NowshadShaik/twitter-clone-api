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

    @GetMapping("/users/listUsers")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) throws Exception {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/users/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody User user) {

        User savedUser = userService.deleteUser(user);

        if(savedUser == null) {
            return new ResponseEntity<>("Invalid user name: " + user.getUsername() + " does not exist", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.ACCEPTED);
    }
}

package com.twitter.backend.controller;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.modals.User;
import com.twitter.backend.services.TweetService;
import com.twitter.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

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
        return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.ACCEPTED);
    }

    @PostMapping("/tweets/add")
    public ResponseEntity<Object> addTweet(@RequestBody Tweet tweet){
        return new ResponseEntity<>(tweetService.postTweet(tweet), HttpStatus.ACCEPTED);
    }

    @PostMapping("/tweets/delete")
    public ResponseEntity<Object> deleteTweet(@RequestBody Tweet tweet){
        return new ResponseEntity<>(tweetService.deleteTweet(tweet), HttpStatus.ACCEPTED);
    }
}

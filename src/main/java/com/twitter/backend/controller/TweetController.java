package com.twitter.backend.controller;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.modals.User;
import com.twitter.backend.services.TweetService;
import com.twitter.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) throws Exception {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);

    }

    @PostMapping("/tweet")
    public ResponseEntity<Object> addTweet(@RequestBody Tweet tweet) throws Exception {

        return new ResponseEntity<>(tweetService.postTweet(tweet), HttpStatus.ACCEPTED);

    }
}

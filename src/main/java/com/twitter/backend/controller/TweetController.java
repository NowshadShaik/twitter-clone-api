package com.twitter.backend.controller;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/twitter")
public class TweetController {

    @Autowired
    TweetService tweetService;


    @GetMapping("/tweets/listByUsername")
    public ResponseEntity<Object> getTweetsByUser(@RequestHeader String username) {
        return new ResponseEntity<>(tweetService.getTweetsByUsername(username), HttpStatus.ACCEPTED);
    }

    @PostMapping("/tweets/add")
    public ResponseEntity<Object> addTweet(@RequestBody Tweet tweet) {
        return new ResponseEntity<>(tweetService.postTweet(tweet), HttpStatus.ACCEPTED);
    }

    @PostMapping("/tweets/update")
    public ResponseEntity<Object> updateTweet(@RequestBody Tweet tweet) throws Exception {
        return new ResponseEntity<>(tweetService.updateTweet(tweet),HttpStatus.ACCEPTED);
    }

    @PostMapping("/tweets/delete")
    public ResponseEntity<Object> deleteTweet(@RequestBody Tweet tweet) {
        return new ResponseEntity<>(tweetService.deleteTweet(tweet), HttpStatus.ACCEPTED);
    }
}

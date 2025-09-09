package com.twitter.backend.controller;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    TweetService tweetService;


    @GetMapping("/listByUsername")
    public ResponseEntity<?> getTweetsByUser(@RequestParam String username) {
        List<Tweet> tweets = tweetService.getTweetsByUsername(username);

        if(tweets == null)
            return new ResponseEntity<>("You are not authorized to list tweets of user: " + username, HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(tweets, HttpStatus.ACCEPTED);
    }

    @PostMapping("/post")
    public ResponseEntity<String> addTweet(@RequestBody Tweet tweet) {
        boolean isPosted = tweetService.postTweet(tweet);

        if(!isPosted)
            return new ResponseEntity<>("You can only post your own tweets.", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("Successfully posted tweet by user: " + tweet.getUsername(), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTweet(@RequestBody Tweet tweet) {

        try {
            tweetService.updateTweet(tweet);
            return new ResponseEntity<>("Tweet updated successfully.",HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTweet(@RequestBody Tweet tweet) {
        boolean isDeleted = tweetService.deleteTweet(tweet);

        if(!isDeleted)
            return new ResponseEntity<>("You can only delete your own tweets.", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>("Successfully posted tweet by user: " + tweet.getUsername(), HttpStatus.CREATED);
    }
}

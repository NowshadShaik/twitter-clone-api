package com.twitter.backend.services;

import com.twitter.backend.Utils.AuthenticationUtils;
import com.twitter.backend.modals.Tweet;
import com.twitter.backend.repositories.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    public boolean postTweet(Tweet tweet) {

        if(!authenticationUtils.getCurrAuthenticatedUser().equals(tweet.getUsername()))
            return false;

        log.info("Posting Tweet by: {}", tweet.getUsername());

        tweet.setId(UUID.randomUUID());
        tweet.setCreated_timeStamp(LocalDateTime.now());
        tweet.setUpdated_timeStamp(LocalDateTime.now());
        tweetRepository.save(tweet);

        log.info("Tweet posted successfully");
        return true;
    }

    public boolean deleteTweet(Tweet tweet) {

        if(!authenticationUtils.getCurrAuthenticatedUser().equals(tweet.getUsername()))
            return false;

        log.info("Deleting tweet....");
        tweetRepository.delete(tweet);
        log.info("Deleted successfully");

        return true;
    }

    public List<Tweet> getTweetsByUsername(String username) {

        if(!authenticationUtils.getCurrAuthenticatedUser().equals(username))
            return null;

        log.info("getting tweets for username: {}", username);
        Optional<List<Tweet>> tweets = tweetRepository.findByUsername(username);

        if(tweets.isEmpty()){
            return new ArrayList<>();
        } else {
            return tweets.get();
        }
    }

    public void updateTweet(Tweet newTweet) throws Exception {

        Optional<Tweet> dbTweet = tweetRepository.findById(newTweet.getId());
        if(dbTweet.isEmpty())
            throw new Exception("Tweet Does not exist.");

        Tweet oldTweet = dbTweet.get();
        if(!authenticationUtils.getCurrAuthenticatedUser().equals(newTweet.getUsername()) || !oldTweet.getUsername().equals(newTweet.getUsername()))
            throw new Exception("You are no the owner of this tweet.");

        log.info("updating the tweet");
        oldTweet.setTweet(newTweet.getTweet());
        oldTweet.setTweetTag(newTweet.getTweetTag());
        oldTweet.setUpdated_timeStamp(LocalDateTime.now());
        tweetRepository.save(oldTweet);
    }
}

package com.twitter.backend.services;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.repositories.TweetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TweetServiceImpl implements TweetService {
    Logger logger = LoggerFactory.getLogger(TweetServiceImpl.class);

    TweetRepository tweetRepository;

    UserService userService;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepo, UserService userService) {
        this.tweetRepository=tweetRepo;
        this.userService=userService;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        logger.info("Posting Tweet by: {}", tweet.getUsername());
        tweet.setId(UUID.randomUUID());
        tweet.setCreated_timeStamp(LocalDateTime.now());
        tweet.setUpdated_timeStamp(LocalDateTime.now());
        tweetRepository.save(tweet);
        logger.info("Tweet posted successfully");
        return tweet;
    }

    @Override
    public Tweet deleteTweet(Tweet tweet) {
        logger.info("Deleting tweet....");
        tweetRepository.delete(tweet);
        logger.info("Deleted successfully");
        return tweet;
    }

    @Override
    public List<Tweet> getTweetsByUsername(String username) {
        List<Tweet> tweets = null;
        if(userService.isUsernameExists(username)) {
            logger.info("getting tweets for username: {}", username);
            tweets = tweetRepository.findByUsername(username);
        } else {
            logger.info("User with username: {} does not exists", username);
        }
        return tweets;
    }

    @Override
    public Tweet updateTweet(Tweet tweet) throws Exception {
        Tweet dbTweet = tweetRepository.findById(tweet.getId());
        if(dbTweet!=null && tweet.getUsername().equals(dbTweet.getUsername())) {
            logger.info("updating the tweet");
            dbTweet.setTweet(tweet.getTweet());
            dbTweet.setTweetTag(tweet.getTweetTag());
            dbTweet.setUpdated_timeStamp(LocalDateTime.now());
            tweetRepository.save(dbTweet);
        } else {
            logger.info("Tweet does not exists or this user is not the owner of tweet.");
            throw new Exception("Tweet does not exists or you are not the owner.");
        }
        return dbTweet;
    }
}

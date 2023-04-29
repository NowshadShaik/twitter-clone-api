package com.twitter.backend.services;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public Tweet postTweet(Tweet tweet) {
        tweet.setId(UUID.randomUUID());
        tweet.setCreated_timeStamp(LocalDateTime.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        tweetRepository.delete(tweet);
    }

}

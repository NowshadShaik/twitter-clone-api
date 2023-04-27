package com.twitter.backend.services;

import com.twitter.backend.modals.Tweet;
import com.twitter.backend.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public Tweet postTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        tweetRepository.delete(tweet);
    }

}

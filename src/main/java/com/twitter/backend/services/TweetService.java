package com.twitter.backend.services;

import com.twitter.backend.modals.Tweet;

import java.util.List;

public interface TweetService {

    Tweet postTweet(Tweet tweet);

    Tweet deleteTweet(Tweet tweet);

    List<Tweet> getTweetsByUsername(String username);

    Tweet updateTweet(Tweet tweet) throws Exception;
}

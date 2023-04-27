package com.twitter.backend.services;

import com.twitter.backend.modals.Tweet;
import org.bson.types.ObjectId;

public interface TweetService {

    Tweet postTweet(Tweet tweet);

    void deleteTweet(Tweet tweet);
}

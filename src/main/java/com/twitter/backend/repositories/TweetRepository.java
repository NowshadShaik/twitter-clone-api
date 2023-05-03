package com.twitter.backend.repositories;

import com.twitter.backend.modals.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface TweetRepository extends MongoRepository<Tweet, String> {

    List<Tweet> findByUsername(String username);

    Tweet findById(UUID uuid);

}

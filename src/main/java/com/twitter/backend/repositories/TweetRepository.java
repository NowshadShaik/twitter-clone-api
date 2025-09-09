package com.twitter.backend.repositories;

import com.twitter.backend.modals.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

    Optional<List<Tweet>> findByUsername(String username);

    Optional<Tweet> findById(UUID uuid);

}

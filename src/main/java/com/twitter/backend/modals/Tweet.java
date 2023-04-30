package com.twitter.backend.modals;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "Tweets")
public class Tweet {

    @Id
    private UUID id;
    @NonNull
    private String username;
    @NonNull
    private String tweet;
    private int likes;
    private LocalDateTime created_timeStamp;
    private List<Tweet> replies;
    @NonNull
    private String tweetTag;

}

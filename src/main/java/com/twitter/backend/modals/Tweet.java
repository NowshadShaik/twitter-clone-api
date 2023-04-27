package com.twitter.backend.modals;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "Tweets")
public class Tweet {

    @Id
    private String id;
    private User user;
    private String tweet;
    private int likes;
    @CreatedDate
    private LocalDateTime created_timeStamp;
    private List<Tweet> replies;
    private String tweetTag;

}

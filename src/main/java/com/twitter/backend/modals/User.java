package com.twitter.backend.modals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private UUID uuid;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String contact;

    public User(String devopsUsername, String devopsPassword) {
        this.username = devopsUsername;
        this.password = devopsPassword;
    }
}

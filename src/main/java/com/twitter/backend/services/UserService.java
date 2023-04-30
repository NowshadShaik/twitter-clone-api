package com.twitter.backend.services;

import com.twitter.backend.modals.User;

import java.util.List;

public interface UserService {

    User createUser(User user) throws Exception;
    List<User> getAllUsers();
    List<User> getUserByUsername(String username) throws Exception;
    User deleteUser(User user);
}
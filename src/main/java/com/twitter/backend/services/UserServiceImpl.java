package com.twitter.backend.services;

import com.twitter.backend.modals.User;
import com.twitter.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    public User createUser(User user) throws Exception {
        logger.info("Registering User {}", user.getUsername());
        user.setUuid(UUID.randomUUID());
        if(!isExistingValidUser(user.getUsername())) {
            userRepository.save(user);
            logger.info("Registration successfully completed for: {}", user.getUsername());
        } else {
            logger.error("Username already exists please use a different username.");
            throw new Exception("Username already exists");
        }
        return user;
    }

    @Override
    public List<String> getAllUsers() {
        logger.info("Getting list of all users.");
        List<User> users= userRepository.findAll();
        List<String> usernames = users.stream().map(u -> u.getUsername()+","+u.getEmail()).toList();
        return usernames;
    }

    @Override
    public List<User> getUserByUsername(String username){
        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public User deleteUser(User user) {

        if(isExistingValidUser(user.getUsername())) {
            logger.info("Deleting user{}", user.getUsername());
            userRepository.deleteByUsername(user.getUsername());
            logger.info("User with {} has been deleted successfully", user.getUsername());
        } else {
            logger.error("Invalid user name: {} does not exist", user.getUsername());
            return null;
        }
        return user;
    }

    @Override
    public boolean isExistingValidUser(String username) {
        logger.info("Verifying if users exists.");
        return userRepository.findByUsername(username) != null;
    }
}

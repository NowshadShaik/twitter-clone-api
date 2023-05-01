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
        logger.info("Registering User "+user.getUsername());
        user.setUuid(UUID.randomUUID());
        if(userRepository.findByUsername(user.getUsername()) != null) {
            logger.error("Username already exists please use a different username.");
            throw new Exception("Username already exists");
        } else {
            userRepository.save(user);
            logger.info("Registration successfully completed for: "+user.getUsername());
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Getting list of all users.");
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserByUsername(String username){
        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public User deleteUser(User user) {
        logger.info("Deleting user" + user.getUsername());
        userRepository.deleteByUsername(user.getUsername());
        logger.info("User with "+user.getUsername() + " has been deleted successfully");
        return user;
    }
}

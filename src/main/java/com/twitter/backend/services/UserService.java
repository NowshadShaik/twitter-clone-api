package com.twitter.backend.services;

import com.twitter.backend.modals.User;
import com.twitter.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    public User createUser(User user) throws Exception {

        logger.info("Registering User: {}", user.getUsername());
        user.setUuid(UUID.randomUUID());

        if(!isExistingValidUser(user.getUsername())) {

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            logger.info("Registration successfully completed for: {}", user.getUsername());

        } else {
            logger.error("Username already exists please use a different username.");
            throw new Exception("Username already exists");
        }
        return user;
    }

    public List<String> getAllUsers() {
        logger.info("Getting list of all users.");
        List<User> users= userRepository.findAll();
        List<String> userNames = users.stream().map(u -> u.getUsername()+", "+u.getEmail()).toList();
        return userNames;
    }

    public List<User> getUserByUsername(String username){
        return userRepository.findByUsernameContaining(username);
    }

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

    public boolean isExistingValidUser(String username) {
        logger.info("Verifying if users exists.");
        return userRepository.findByUsername(username) != null;
    }

    public String login(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            return "Logged in with user: " + user.getUsername() + " \nToken: " + token;
        }
        return "Login Failed";
    }
}

package com.twitter.backend.services;

import com.twitter.backend.Utils.AuthenticationUtils;
import com.twitter.backend.modals.User;
import com.twitter.backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Autowired
    private JWTService jwtService;

    public User createUser(User user) throws Exception {

        log.info("Registering User: {}", user.getUsername());
        user.setUuid(UUID.randomUUID());

        if(!isExistingValidUser(user.getUsername())) {

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("Registration successfully completed for: {}", user.getUsername());

        } else {
            log.error("Username already exists please use a different username.");
            throw new Exception("Username already exists");
        }
        return user;
    }

    public List<String> getAllUsers() {
        log.info("Getting list of all users.");
        List<User> users= userRepository.findAll();
        List<String> userNames = users.stream().map(u -> u.getUsername()+", "+u.getEmail()).toList();
        return userNames;
    }

    public List<User> getUserByUsername(String username){
        return userRepository.findByUsernameContaining(username);
    }

    public boolean deleteUser(String username) {

        if(!authenticationUtils.getCurrAuthenticatedUser().equals(username)) {
            return false;
        }

        log.info("Deleting user with username: {}", username);
        userRepository.deleteByUsername(username);
        log.info("User with {} has been deleted successfully", username);
        return true;
    }

    public boolean isExistingValidUser(String username) {
        log.info("Verifying if users exists.");
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

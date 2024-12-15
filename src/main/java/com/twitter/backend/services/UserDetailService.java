package com.twitter.backend.services;

import com.twitter.backend.modals.UserPrincipal;
import com.twitter.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.twitter.backend.modals.User;

@Service
public class UserDetailService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            logger.error("User with user name: {} does not exist", username);
            throw new UsernameNotFoundException("User not found");
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}

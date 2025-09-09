package com.twitter.backend.services;

import com.twitter.backend.modals.UserPrincipal;
import com.twitter.backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.twitter.backend.modals.User;

@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Value("${devops.username}")
    String devopsUsername;

    @Value("${devops.password}")
    String devopsPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.equals(devopsUsername)) {
            return new UserPrincipal(new User(devopsUsername, devopsPassword));
        }

        User user = userRepository.findByUsername(username);

        if(user == null) {
            log.error("User with user name: {} does not exist", username);
            throw new UsernameNotFoundException("User not found");
        }

        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}

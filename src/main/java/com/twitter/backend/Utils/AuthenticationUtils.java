package com.twitter.backend.Utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

    public String getCurrAuthenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

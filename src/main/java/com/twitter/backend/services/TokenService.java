package com.twitter.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    public static final String SECRET_TOKEN = "jkfhJFBDl5j685JFDjkfgb";

    public String createToken (UUID id) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_TOKEN);
            String token = JWT.create()
                    .withClaim("userId", id.toString())
                    .withClaim("createdAt", new Date())
                    .sign(algorithm);
            return token;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getUserIdFromToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_TOKEN);
            JWTVerifier verifier =JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidToken(String token) {
        String userId = this.getUserIdFromToken(token);
        return userId!=null;
    }
}

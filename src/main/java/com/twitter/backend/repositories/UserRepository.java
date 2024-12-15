package com.twitter.backend.repositories;

import java.util.List;
import com.twitter.backend.modals.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    List<User> findAll();

    List<User> findByUsernameContaining(String username);

    void deleteByUsername(String username);
}

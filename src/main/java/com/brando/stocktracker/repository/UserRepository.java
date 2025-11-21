package com.brando.stocktracker.repository;

import com.brando.stocktracker.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<User, String> {

     UserDetails findByEmail(String email);
     boolean existsByEmail(String email);
}
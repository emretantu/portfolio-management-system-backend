package com.pmsystemtest.microservices.jwtservice.repository;

import com.pmsystemtest.microservices.jwtservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


}

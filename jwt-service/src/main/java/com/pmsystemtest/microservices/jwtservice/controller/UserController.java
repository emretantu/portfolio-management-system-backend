package com.pmsystemtest.microservices.jwtservice.controller;

import com.pmsystemtest.microservices.jwtservice.entity.User;
import com.pmsystemtest.microservices.jwtservice.exception.UserNotFoundException;
import com.pmsystemtest.microservices.jwtservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public boolean isExistUser(@PathVariable Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return true;
        }else{
            throw new UserNotFoundException();
        }
    }

    @GetMapping()
    public List<User> isExistUser(){
        return userRepository.findAll();
    }
}

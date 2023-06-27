package com.example.bloghw2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bloghw2.user.entity.User;
import com.example.bloghw2.user.repository.UserRepository;

@Component
public class UserSetup {

    @Autowired
    private UserRepository userRepository;

    public Long saveUser(User user){
        return userRepository.save(user).getId();
    }

    public void clearUsers(){
        userRepository.deleteAll();
    }
}
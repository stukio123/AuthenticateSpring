package com.example.userservice.Services;

import com.example.userservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository userRepository;

    public int enableUser(String username){
        return userRepository.enableUser(username);
    }

}

package com.example.userservice.Services;

import com.example.userservice.Models.User;
import com.example.userservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository userRepository;

    public int enableUser(String username){
        return userRepository.enableUser(username);
    }

    public boolean isExist(String username){return userRepository.findByUsername(username).isPresent();}

    public boolean isEmailExist(String email){return userRepository.findByEmail(email).isPresent();}

    public Long save(User user){
        return userRepository.save(user).getId();
    }
}

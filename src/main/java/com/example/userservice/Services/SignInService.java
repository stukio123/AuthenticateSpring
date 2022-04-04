package com.example.userservice.Services;

import com.example.userservice.Models.SignInRequest;
import com.example.userservice.Models.User;
import com.example.userservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignInService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public String signIn(SignInRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if(user == null){
            return "Username not found";
        }
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        if(!user.getPassword().equals(encodedPassword)){
            return "Username or Password incorrect !";
        }
        if(user.getLocked()){
            return "Account is locked";
        }
        if(!user.getEnabled()){
            return "Please confirm your account";
        }

        return "Login Successful !";
    }
}

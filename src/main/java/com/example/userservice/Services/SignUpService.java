package com.example.userservice.Services;

import com.example.userservice.Models.SignUpRequest;
import com.example.userservice.Models.Token;
import com.example.userservice.Models.User;
import com.example.userservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SignUpService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserService userService;
    private final long expiredTime = 42; //Hours
    /**
     * if UserName is already exist -> throw error messages
     * else encode password of User. Save User and create token
     * @param request
     * @return Token generated
     */
    public String signUp(SignUpRequest request){
        boolean exist = userRepository.findByUsername(request.getUsername()).isPresent();
        if(exist){
            throw new IllegalStateException(String.format("%s already exist",request.getUsername()));
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        User newUser = request.mapToUser();
        userRepository.save(newUser);
        String payload = UUID.randomUUID().toString();
        Token token = new Token(
                payload,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(expiredTime),
                newUser
        );

        tokenService.saveToken(token);

        return payload;
    }

    @Transactional
    public String confirmToken(String payload){
        Token token = tokenService.getToken(payload).orElseThrow(()->
                new IllegalStateException("404 ! Something wrong..."));

        if(token.getConfirmedAt() != null){
            throw new IllegalStateException("Account already confirmed !");
        }

        LocalDateTime expiredAt = token.getExpiredAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Confirmation is expired");
        }

        tokenService.setConfirmedAt(payload);
        userService.enableUser(token.getUser().getUsername());

        return "Account have been confirmed successful !!";
    }
}

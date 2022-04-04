package com.example.userservice.Services;

import com.example.userservice.Models.SignUpRequest;
import com.example.userservice.Models.Token;
import com.example.userservice.Models.User;
import com.example.userservice.Repositories.UserRepository;
import com.example.userservice.Utils.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SignUpService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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
        boolean exist = userService.isExist(request.getUsername());
        if(exist){
            return String.format("%s already exist",request.getUsername());
        }
        boolean valid = EmailValidator.isEmail(request.getEmail());
        if(valid){
            return String.format("%s email is invalid !");
        }else{
            exist = userService.isEmailExist(request.getEmail());
            if(exist){
                return String.format("%s already exist",request.getEmail());
            }
        }
        if(request.getPassword().equals(null)){
            return "Password can't be null";
        }
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        String a = bCryptPasswordEncoder.encode("AA");
        User newUser = request.mapToUser();
        userService.save(newUser);
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

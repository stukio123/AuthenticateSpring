package com.example.userservice.Services;

import com.example.userservice.DTO.UserDTO;
import com.example.userservice.Models.ResponseMessages;
import com.example.userservice.Models.Token;
import com.example.userservice.Models.User;
import com.example.userservice.Models.UserRoles;
import com.example.userservice.Repositories.UserRepository;
import com.example.userservice.Utils.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final long expiredTime = 42; //Hours

    public int enableUser(String username){
        return userRepository.enableUser(username);
    }

    public boolean isExist(String username){return userRepository.findByUsername(username).isPresent();}

    public boolean isEmailExist(String email){return userRepository.findByEmail(email).isPresent();}

    public Long save(User user){
        return userRepository.save(user).getId();
    }

    public String signIn(UserDTO request){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if(user == null){
            return "Username not found";
        }
        if(!user.getPassword().equals(request.getPassword())){
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

    public ResponseMessages signUp(UserDTO request){
        ResponseMessages responseMessages = new ResponseMessages();
        boolean exist = this.isExist(request.getUsername());
        if(exist){
            responseMessages.setMessages(String.format("%s already exist",request.getUsername()));
            responseMessages.setCode(HttpStatus.NOT_ACCEPTABLE.name());
            return responseMessages;
        }
        boolean valid = EmailValidator.isEmail(request.getEmail());
        if(valid){
            responseMessages.setMessages(String.format("%s email is invalid !"));
            responseMessages.setCode(HttpStatus.NOT_ACCEPTABLE.name());
            return responseMessages;
        }else{
            exist = this.isEmailExist(request.getEmail());
            if(exist){
                responseMessages.setMessages(String.format("%s already exist",request.getEmail()));
                responseMessages.setCode(HttpStatus.NOT_ACCEPTABLE.name());
                return responseMessages;
            }
        }
        if(request.getPassword().equals(null)){
            responseMessages.setMessages("Password can't be null");
            responseMessages.setCode(HttpStatus.NOT_ACCEPTABLE.name());
            return responseMessages;
        }
//        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
//        request.setPassword(encodedPassword);
        User newUser = request.mapToUser();
        newUser.setUserRoles(UserRoles.USER);
        this.save(newUser);
        String payload = UUID.randomUUID().toString();
        Token token = new Token(
                payload,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(expiredTime),
                newUser
        );
        tokenService.saveToken(token);
        responseMessages.setMessages("Sign Up Sucessful !!");
        responseMessages.setCode(HttpStatus.CREATED.name());
        responseMessages.setData(payload);

        return responseMessages;
    }

    public User findUserByToken(String token){
        return userRepository.findUserByToken(token).orElse(null);
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
        this.enableUser(token.getUser().getUsername());

        return "Account have been confirmed successful !!";
    }
}

package com.example.userservice.Controllers;

import com.example.userservice.DTO.UserDTO;
import com.example.userservice.Models.ResponseMessages;
import com.example.userservice.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Primary
public class UserControllers {
    private final UserService userService;

    @GetMapping(value={"api/v1/users","api/v1/users/"})
    public String HelloWord(){
        return "Hello World";
    }

    @PostMapping(value={"api/v1/users/registration"})
    public ResponseMessages signUp(@RequestBody UserDTO request){
        return userService.signUp(request);
    }

    @GetMapping(path="api/v1/users/registration/confirm")
    public String confirmSignUp(@RequestParam("token") String token){
        return userService.confirmToken(token);
    }

    @PostMapping("api/v1/users/signIn")
    public String signIn(@RequestBody UserDTO request){
        return userService.signIn(request);
    }

}

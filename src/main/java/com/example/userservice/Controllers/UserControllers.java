package com.example.userservice.Controllers;

import com.example.userservice.Models.SignUpRequest;
import com.example.userservice.Services.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserControllers {

    private final SignUpService signUpService;

    @GetMapping(value={"","/"})
    public String HelloWord(){
        return "Hello World";
    }

    @PostMapping(value={"/registration"})
    public String SignUp(@RequestBody SignUpRequest request){
        return signUpService.signUp(request);
    }

    @GetMapping(path="/registration/confirm")
    public String confirmSignUp(@RequestParam("token") String token){
        return signUpService.confirmToken(token);
    }
}

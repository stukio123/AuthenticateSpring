package com.example.userservice.Controllers;

import com.example.userservice.DTO.UserDTO;
import com.example.userservice.Models.ResponseMessages;
import com.example.userservice.Models.User;
import com.example.userservice.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class AccountControllers {

    private final UserService userService;

    public AccountControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String login(Model model){
        String error = null;
        model.addAttribute("messages",error);
        return "signin";
    }

    @PostMapping("/signin")
    public String login(Model model, @ModelAttribute UserDTO dto){
        String user = userService.signIn(dto);
        model.addAttribute("messages",user);
        return "signin";
    }

    @GetMapping("/signup")
    public String register(Model model){
        String error = null;
        model.addAttribute("messages",error);
        return "signup";
    }

    @PostMapping("/signup")
    public String register(Model model, @ModelAttribute UserDTO dto){
        ResponseMessages user = userService.signUp(dto);
        model.addAttribute("messages",user.getMessages());
        model.addAttribute("data",user.getData());
        return "signup";
    }

    @GetMapping("/confirm/{token}")
    public String confirmation(Model model, @PathVariable String token){
        String messages = userService.confirmToken(token);
        model.addAttribute("messages",messages);
        User user = userService.findUserByToken(token);
        model.addAttribute("user",user);
        return "confirm";
    }

}

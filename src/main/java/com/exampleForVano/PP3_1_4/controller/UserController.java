package com.exampleForVano.PP3_1_4.controller;


import com.exampleForVano.PP3_1_4.Service.UserService;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("roles", userService.findByName(user.getUsername()).getRoles());
        model.addAttribute("user", userService.findByName(user.getUsername()));
        return "user";
    }
}

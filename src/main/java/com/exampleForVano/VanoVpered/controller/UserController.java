package com.exampleForVano.VanoVpered.controller;

import com.exampleForVano.VanoVpered.Service.UserServiceIpm;
import com.exampleForVano.VanoVpered.model.User;
import com.exampleForVano.VanoVpered.security.UserDetailsImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceIpm userService;

    public UserController(UserServiceIpm userService) {
        this.userService = userService;
    }
    @GetMapping
    public String getUserPage(Model model, Principal principal) {
        UserDetailsImp user = (UserDetailsImp) userService.loadUserByUsername(principal.getName());
        User user1 = user.getUser();
        model.addAttribute("user", user1);
        return "user";
    }
}

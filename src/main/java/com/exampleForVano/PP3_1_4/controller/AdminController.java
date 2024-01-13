package com.exampleForVano.PP3_1_4.controller;

import com.exampleForVano.PP3_1_4.Service.RoleServiceImp;
import com.exampleForVano.PP3_1_4.Service.UserServiceIpm;
import com.exampleForVano.PP3_1_4.model.User;


import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserServiceIpm userService;
    private final RoleServiceImp roleService;

    public AdminController(UserServiceIpm userService, RoleServiceImp roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping
    public String allUsers(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, Model model) {
        model.addAttribute("user", userService.findByName(userDetails.getUsername()));
        model.addAttribute("userList", userService.getUserList());
        model.addAttribute("roleList", roleService.getRoleList());

        return "admin";
    }
    @PostMapping
    public String createNewUser(@ModelAttribute("user") User user,
                                @RequestParam(value = "nameRoles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("{id}/edit")
    public String editUserForm(@ModelAttribute("user") User user,
                               ModelMap model,
                               @PathVariable("id") long id,
                               @RequestParam(name = "editRoles", required = false) String[] roles) {
        if (roles != null) {
            user.setRoles(roleService.getSetOfRoles(roles));
        }
        model.addAttribute("roles", roleService.getRoleList());
        model.addAttribute("user", userService.findById(id));
        return "admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(name = "editRoles", required = false) String[] roles) {
        if (roles != null) {
            user.setRoles(roleService.getSetOfRoles(roles));
        }
        userService.save(user);
        return "redirect:/admin/";
    }
    @PostMapping("/{id}/remove")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin/";
    }


}

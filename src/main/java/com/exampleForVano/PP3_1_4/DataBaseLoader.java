package com.exampleForVano.PP3_1_4;

import com.exampleForVano.PP3_1_4.Service.RoleServiceImp;
import com.exampleForVano.PP3_1_4.Service.UserService;
import com.exampleForVano.PP3_1_4.model.Role;
import com.exampleForVano.PP3_1_4.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;

@Component
public class DataBaseLoader implements CommandLineRunner {
    private final UserService userService;
    private final RoleServiceImp roleService;

    public DataBaseLoader(UserService userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        Role ROLE_USER = new Role("ROLE_USER");
        roleService.save(ROLE_USER);

        Role ROLE_ADMIN = new Role("ROLE_ADMIN");
        roleService.save(ROLE_ADMIN);

        User user = new User("user", "Abuser", 13, "fafi@gmail.ru", "1234");
        user.setRoles(new HashSet<>(List.of(ROLE_USER)));
        userService.save(user);

        User admin = new User("Admin","Controller", 33, "joja@gmail.ru", "123456");
        admin.setRoles(new HashSet<>(List.of(ROLE_ADMIN, ROLE_USER)));
        userService.save(admin);


    }
}

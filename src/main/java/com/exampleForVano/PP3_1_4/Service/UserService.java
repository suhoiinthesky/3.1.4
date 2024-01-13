package com.exampleForVano.PP3_1_4.Service;


import com.exampleForVano.PP3_1_4.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> getUserList();

    void delete(User user);

    User findById(Long id);
    UserDetails loadUserByUsername(String username);
    User findByName(String name);

}

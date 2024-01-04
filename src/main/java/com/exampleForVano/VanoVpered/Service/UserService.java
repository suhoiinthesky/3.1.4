package com.exampleForVano.VanoVpered.Service;


import com.exampleForVano.VanoVpered.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> getUserList();

    void delete(User user);

    User findById(Long id);
}

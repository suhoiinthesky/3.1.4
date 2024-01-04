package com.exampleForVano.VanoVpered.Service;

import com.exampleForVano.VanoVpered.model.User;
import com.exampleForVano.VanoVpered.repository.UserRepository;
import com.exampleForVano.VanoVpered.security.UserDetailsImp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserServiceIpm implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceIpm(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }


    /**
     * Тут стоило проверить на существование юзера
     * Но опять таки, это все тестовые приложения, которые абсолютно
     * не предназаначаются для продакшена.
     * Просто хотел дать понять что да - это делать по идее надо.
     */
    @Override
    @Transactional
    public User findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new UserDetailsImp(user);
    }
}

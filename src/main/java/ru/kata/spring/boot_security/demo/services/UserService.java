package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void deleteUserById(Long id);

    User getUserById(Long id);

    void saveUser(User user);

    User findByUsername(String userName);

    User updateUserById(Long id, User user);

}










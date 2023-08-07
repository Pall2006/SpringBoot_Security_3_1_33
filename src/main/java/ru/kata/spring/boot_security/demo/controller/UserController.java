package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servises.RoleService;
import ru.kata.spring.boot_security.demo.servises.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String AdminPage() {
        return "admin";
    }

    @GetMapping("/all-users")
    public String allUser(Model model) {
        //Получим всех людей и отправим на представление
        model.addAttribute("users", userService.listAll());
        return "admin/all-users";
    }

    @GetMapping("/all-users/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        // Получим одного человека и отправим на представление
        model.addAttribute("user", userService.findUserById(id));
//        List<Role> userRoles = userService.getRoles();
//        model.addAttribute("userRoles", userRoles);
        return "/admin/show";
    }

    @GetMapping("all-users/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        List<Role> allRoles = roleService.listAllRoles(); // Получим список всех ролей
        model.addAttribute("allRoles", allRoles); // Передадим список ролей в представление
        return "admin/new-user";
    }

    @PostMapping("/all-users")
    public String createUser(@ModelAttribute("user") User user) {
        //Создадим нового юсера и сохраним в базе данный
        userService.addUser(user);
        return "redirect:/admin/all-users";
    }


    @GetMapping("/all-users/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        List<Role> allRoles = roleService.listAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "/admin/update-user";
    }

    @PatchMapping("/all-users/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUserById(id, user);
        return "redirect:/admin/all-users/";
    }

    @DeleteMapping("/all-users/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/all-users";
    }
}


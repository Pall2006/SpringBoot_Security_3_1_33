package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String getAdminProfile(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName(); // Получение имени пользователя из Principal

            model.addAttribute("user", userService.findByUsername(username));
        }
        return "user";
    }

    @GetMapping("/all-users")
    public String allUser(Model model) {
        //Получим всех людей и отправим на представление
        model.addAttribute("users", userService.getAllUsers());
        return "admin/all-users";
    }

    @GetMapping("/all-users/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        // Получим одного человека и отправим на представление
        model.addAttribute("user", userService.getUserById(id));
        return "admin/show";
    }

    @GetMapping("all-users/new")
    public String newUser(@ModelAttribute User user, Model model) {
        List<Role> allRoles = roleService.getListAllRoles(); // Получим список всех ролей
        model.addAttribute("allRoles", allRoles); // Передадим список ролей в представление
        return "admin/new-user";
    }

    @PostMapping("/all-users")
    public String createUser(@ModelAttribute User user) {
        //Создадим нового юсера и сохраним в базе данный
        userService.saveUser(user);
        return "redirect:/admin/all-users";
    }


    @GetMapping("/all-users/{id}/edit")
    public String edit(ModelMap model, @PathVariable Long id) {
        model.addAttribute("user", userService.getUserById(id));
        List<Role> allRoles = roleService.getListAllRoles();
        model.addAttribute("allRoles", allRoles);
        return "/admin/update-user";
    }

    @PatchMapping("/all-users/{id}")
    public String update(@ModelAttribute User user, @PathVariable Long id) {
        userService.updateUserById(id, user);
        return "redirect:/admin/all-users/";
    }


    @DeleteMapping("/all-users/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/all-users";
    }
}


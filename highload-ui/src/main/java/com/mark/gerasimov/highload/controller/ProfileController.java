package com.mark.gerasimov.highload.controller;


import com.mark.gerasimov.highload.dao.UserDao;
import com.mark.gerasimov.highload.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
public class ProfileController {

    private final UserDao userDao;

    public ProfileController(UserDao userDao) {
        this.userDao = userDao;
    }


    @GetMapping("/u/{id}")
    public String profile(Model model, @PathVariable String id) {
        final Optional<User> userProfile = userDao.findById(UUID.fromString(id));
        if (userProfile.isPresent()) {
            model.addAttribute("user", userProfile.get());
            return "profile";
        } else {
            return "404";
        }
    }

    @GetMapping("/register")
    public String edit(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        return "redirect:/u/" + userDao.save(user).getId();
    }
}

package com.mark.gerasimov.highload.controller;


import com.mark.gerasimov.highload.dao.CitiesDao;
import com.mark.gerasimov.highload.dao.InterestsDao;
import com.mark.gerasimov.highload.dao.UserDao;
import com.mark.gerasimov.highload.dao.UserInterestsDao;
import com.mark.gerasimov.highload.model.Gender;
import com.mark.gerasimov.highload.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ProfileController {

    private final UserDao userDao;
    private final CitiesDao citiesDao;
    private final InterestsDao interestsDao;
    private final UserInterestsDao userInterestsDao;

    public ProfileController(
            UserDao userDao,
            CitiesDao citiesDao,
            InterestsDao interestsDao,
            UserInterestsDao userInterestsDao) {
        this.userDao = userDao;
        this.citiesDao = citiesDao;
        this.interestsDao = interestsDao;
        this.userInterestsDao = userInterestsDao;
    }


    @GetMapping("/u/{id}")
    public String profile(Model model, @PathVariable String id) {
        final Optional<User> userProfile = userDao.findById(UUID.fromString(id));
        if (userProfile.isPresent()) {
            final User user = userProfile.get();
            model.addAttribute("user", user);
            model.addAttribute("city", citiesDao.findById(user.getCityId()));
            model.addAttribute("interests", userInterestsDao.findAllByUserId(user.getId()));
            return "profile";
        } else {
            return "404";
        }
    }

    @GetMapping("/register")
    public String edit(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("cities", citiesDao.findAll());
        model.addAttribute("cityId", null);
        model.addAttribute("interests", interestsDao.findAll());
        model.addAttribute("gender", "");
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, User user, @RequestParam(value = "interestsId", required = false) UUID[] interestsId) {
        final String gender = (String) model.getAttribute("gender");
        if (gender != null && !gender.isEmpty()) {
            user.setGender(Gender.valueOf(gender));
        }

        final UUID userId = userDao.save(user).getId();

        if (interestsId != null && interestsId.length > 0) {
            userInterestsDao.saveUserInterests(userId, Arrays.asList(interestsId));
        }

        return "redirect:/u/" + userId;
    }
}

package com.yuhelper.core.controller;

import com.yuhelper.core.domain.security.model.SignUpToken;
import com.yuhelper.core.model.User;
import com.yuhelper.core.service.TokenService;
import com.yuhelper.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;


@Controller
public class UserController {
    // TODO ADD 404 PAGES
    @Autowired
    UserService userService;

    @Resource(name = "UserBean")
    User user;

    @Autowired
    TokenService tokenService;


    // TODO make expiredToken.html
    @GetMapping(value = "/users/verify")
    public ModelAndView enableUser(@RequestParam String token, HttpServletResponse response) {
        Optional<SignUpToken> signUpToken = tokenService.getSignUpToken(token);
        if (signUpToken.isPresent()) {
            if (signUpToken.get().getExpiryTime().after(new Date())) {
                tokenService.enableUser(signUpToken.get());
                return new ModelAndView("forward:/home.html");
            } else {
                return new ModelAndView("forward:/expiredToken.html");
            }
        } else {
            return new ModelAndView("forward:/home.html");
        }
    }

    @GetMapping(value = "/user/{userProfile}")
    public ModelAndView getUserProfile(@PathVariable("userProfile") String q) {
        Optional<User> userProfile = userService.getUser(q);
        ModelAndView model;
        if (userProfile.isPresent()) {
            model = new ModelAndView("profile.html");
            model.addObject("userProfile", userProfile.get());
            model.addObject("userInfo", userService.getUserProfile(userProfile.get()));
            userService.addUserToModel(model);
            if (userProfile.get().equals(user)) {
                model.addObject("userAdmin", true);
            }
        } else {
            model = new ModelAndView("forward:/home.html");
        }
        return model;

    }

    @GetMapping(value = "/user/settings")
    public ModelAndView getUserSettingsPage() {
        ModelAndView model = new ModelAndView("userSettings.html");
        userService.addUserToModel(model);
        return model;
    }

}

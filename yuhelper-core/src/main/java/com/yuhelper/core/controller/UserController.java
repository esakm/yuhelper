package com.yuhelper.core.controller;

import com.yuhelper.core.domain.security.model.SignUpToken;
import com.yuhelper.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    TokenService tokenService;

    @GetMapping(value = "/users/verify")
    public ModelAndView enableUser(@RequestParam String token, HttpServletResponse response){
        Optional<SignUpToken> signUpToken = tokenService.getSignUpToken(token);
        if(signUpToken.isPresent()){
            if(signUpToken.get().getExpiryTime().after(new Date())){
                tokenService.enableUser(signUpToken.get());
                return new ModelAndView("login.html");
            }else{
                return new ModelAndView("expiredToken.html");
            }
        }else{
            return new ModelAndView("login.html");
        }
    }
}

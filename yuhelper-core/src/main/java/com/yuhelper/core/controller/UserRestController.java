package com.yuhelper.core.controller;

import com.yuhelper.core.model.User;
import com.yuhelper.core.service.EmailService;
import com.yuhelper.core.service.TokenService;
import com.yuhelper.core.service.UserService;
import com.yuhelper.core.utils.validator.annotation.ValidEmail;
import com.yuhelper.core.utils.validator.annotation.ValidPassword;
import com.yuhelper.core.utils.validator.annotation.ValidUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@Validated
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    TokenService tokenService;


    @PostMapping(value = "/users/add")
    public void createNewUser(@RequestParam @ValidUsername String username,
                              @RequestParam @ValidEmail String email,
                              @RequestParam @ValidPassword String password,
                              @RequestParam @ValidPassword String passwordCheck,
                              HttpServletResponse response) {
        if(userService.checkNewUsernameAndEmail(username, email)){
            if(userService.checkPassword(password, passwordCheck)){
                User user = userService.createUser(username, email, password);
                emailService.sendVerificationLink(email, user.getSignUpToken().getToken());
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @GetMapping(value = "/users/resend")
    public void resendVerificationEmail(@RequestParam @ValidUsername String username){
        emailService.resendVerificationLink(username);
    }

}

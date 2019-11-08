package com.yuhelper.core.controller;


import com.yuhelper.core.model.User;
import com.yuhelper.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    @Autowired
    UserService userService;

    @Resource(name = "UserBean")
    User user;

    @GetMapping(value={"/", "home", "index"})
    public String redirectToIndex(){
        return "forward:/home.html";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpSession session) {
        return "login.html";
    }


    @GetMapping(value = "home.html")
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("home.html");
        if(user.getId() != null){
            model.addObject("user", user);
        }
        return model;
    }

    @GetMapping(value = "signup")
    public String signUpPage(){
        return "signup.html";
    }

}

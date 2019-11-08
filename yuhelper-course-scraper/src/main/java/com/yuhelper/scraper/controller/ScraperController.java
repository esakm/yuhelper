package com.yuhelper.scraper.controller;


import com.yuhelper.core.model.User;
import com.yuhelper.scraper.util.ScraperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;



@Controller
public class ScraperController {

    @Resource(name = "scraperManagerBean")
    @Autowired
    ScraperManager scraperManager;

    @Resource(name="UserBean")
    @Autowired
    User user;

    @GetMapping(value = "/scraper")
    public String startScraper() {
        return scraperManager.runScraperWithResult() ? "forward:/home.html" :
                "forward:/login";

    }

}

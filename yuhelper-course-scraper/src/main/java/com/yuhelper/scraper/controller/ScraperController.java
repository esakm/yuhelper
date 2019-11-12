package com.yuhelper.scraper.controller;


import com.yuhelper.scraper.util.ScraperManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;



@Controller
public class ScraperController {

    @Resource(name = "scraperManagerBean")
    @Autowired
    ScraperManager scraperManager;

    @GetMapping(value = "/scraper")
    public String startScraper() {
        return scraperManager.runScraperWithResult() ? "forward:/home.html" :
                "forward:/login";

    }

}

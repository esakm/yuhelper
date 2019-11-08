package com.yuhelper.servlet.config;

import com.yuhelper.core.repo.UserRepository;
import com.yuhelper.scraper.util.ScraperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class Config {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScraperManager scraperManager;


    @Scheduled(cron = "0 0 9 * * *")
    public void deleteExpiredUsers() {
        userRepository.deleteExpiredUsers(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
    }

    // USE UTC TIME FOR EC-2
    @Scheduled(cron = "0 0 8 * * *")
    public void runScraper() {
        scraperManager.runScraper();
    }

}

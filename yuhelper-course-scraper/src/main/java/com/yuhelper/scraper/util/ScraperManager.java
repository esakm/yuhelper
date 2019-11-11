package com.yuhelper.scraper.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;


@Component
public class ScraperManager {

    public static ProcessBuilder pb;
    public static Process process;

    @Resource(name = "scraperURLBean")
    @Autowired
    private String scraperUrl;

    public ScraperManager() {

    }

    private boolean startProcess() {
        try {
            pb = new ProcessBuilder("python3", scraperUrl);
            pb.inheritIO();
            Process p = pb.start();
            process = p;
            return true;
        } catch (IOException e) {
            try {
                pb = new ProcessBuilder("python", scraperUrl);
                pb.inheritIO();
                Process p = pb.start();
                process = p;
                return true;
            } catch (IOException e1) {
                return false;
            }
        }
    }

    public void runScraper() {
        if (pb != null) {
            if (process != null && process.isAlive()) {
                return;
            } else {
                startProcess();
            }
        } else {
            startProcess();
        }
    }

    public boolean runScraperWithResult() {
        if (pb != null) {
            if (process != null && process.isAlive()) {
                return false;
            } else {
                return startProcess();
            }
        } else {
            return startProcess();
        }
    }

}

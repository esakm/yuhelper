package com.yuhelper.scraper.config;

import com.yuhelper.scraper.util.ScraperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ScraperConfig {

    @Autowired
    ScraperManager scraperManager;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public String scraperURLBean() {
        try {
            InputStream scraperStream = ScraperConfig.class.getResourceAsStream("/scraper/scraper.py");
            InputStream configStream = ScraperConfig.class.getResourceAsStream("/scraper/semesters.json");
            URL scraperPath = ScraperConfig.class.getResource("/scraper/scraper.py");
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");
            FileSystems.newFileSystem(scraperPath.toURI(), env);
            extractFile("./semesters.json", configStream);
            return extractFile("./scraper.py", scraperStream);
        } catch (URISyntaxException e) {
            System.exit(4004);

        }
        // WE'RE IN WINDOWS
        catch (IOException e) {
            URL scraperUrl = ScraperConfig.class.getResource("/scraper/scraper.py");
            try {

                Path path = Paths.get(scraperUrl.toURI()).toAbsolutePath();
                return path.toString();
            } catch (URISyntaxException e1) {
                System.exit(4004);
            }
        } catch(IllegalArgumentException e){
            URL scraperUrl = ScraperConfig.class.getResource("/scraper/scraper.py");
            try {

                Path path = Paths.get(scraperUrl.toURI()).toAbsolutePath();
                return path.toString();
            } catch (URISyntaxException e1) {
                System.exit(4004);
            }
        }
        return null;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public ScraperManager scraperManagerBean() {
        return new ScraperManager();
    }


    public String extractFile(String name, InputStream stream) throws IOException {
        File newFile = new File(name);
        try {
            if (newFile.exists()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
                int line;
                while ((line = stream.read()) != -1) {
                    bw.write(line);
                }
                stream.close();
                bw.flush();
                bw.close();
            } else {
                Path newPath = Files.createFile(newFile.toPath());
                BufferedWriter bw = new BufferedWriter(new FileWriter(newPath.toFile()));
                int line;
                while ((line = stream.read()) != -1) {
                    bw.write(line);
                }
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            throw e;
        }
        return Paths.get(newFile.toURI()).toAbsolutePath().toString();
    }


}

package com.yuhelper.servlet;

import com.yuhelper.core.model.Course;
import com.yuhelper.core.repo.CourseRepository;
import com.yuhelper.core.repo.CustomRepositoryImpl;
import com.yuhelper.core.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.*;


@SpringBootApplication(scanBasePackages = {"com"})
@EnableJpaRepositories(basePackages = {"com.yuhelper.core.repo", "com.yuhelper.core.domain.security.repo"}, repositoryBaseClass = CustomRepositoryImpl.class)
@ComponentScan({"com.yuhelper"})
@EntityScan("com.yuhelper")
@EnableCaching
public class Main {
    public static void main(String[] args){
        Map<String, String> env = System.getenv();
        System.out.println(env.get("YUHELPER_PASSWORD"));
        SpringApplication.run(Main.class, args);
    }
}


package com.yuhelper.core.controller;


import com.yuhelper.core.model.Course;
import com.yuhelper.core.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseRestController {

    @Autowired
    CourseService courseService;

    @GetMapping(value = "/courses/autocomplete")
    public List<Course> courseAutoComplete(@RequestParam String course){
        return courseService.searchForAutoComplete(course);
    }

}

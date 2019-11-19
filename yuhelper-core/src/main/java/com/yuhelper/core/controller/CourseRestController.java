package com.yuhelper.core.controller;


import com.yuhelper.core.model.Course;
import com.yuhelper.core.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
public class CourseRestController {

    @Autowired
    CourseService courseService;

    @GetMapping(value = "/courses/autocomplete")
    public List<Course> courseAutoComplete(@RequestParam String course) {
        return courseService.searchForAutoComplete(course);
    }

    @GetMapping(value = "/api/course")
    public Course getCourse(@RequestParam String q, HttpServletResponse response) {
        Optional<Course> course = courseService.searchForRest(q);
        if (course.isPresent()) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return course.get();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

}

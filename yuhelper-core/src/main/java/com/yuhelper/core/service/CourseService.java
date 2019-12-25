package com.yuhelper.core.service;

import com.yuhelper.core.model.Course;
import com.yuhelper.core.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepo;

    private List<Course> courses;

    @PostConstruct
    private void loadCourses(){
        courses = courseRepo.findAll();
    }

    public Course searchByName(String course){
        return courseRepo.searchByCourseCodeAndCredits(course);
    }

    @Cacheable("course-autocomplete")
    public List<Course> searchForAutoComplete(String q){
        final String keyword = q.toLowerCase();
        List<Course> results = courses.stream().filter((Course c) -> c.getCoursePK().getCourseCode().toLowerCase().contains(keyword) || c.getName().toLowerCase().contains(keyword)).collect(Collectors.toList());
        return results.size() >= 10 ? results.subList(0, 10) : results;
    }

    @Cacheable("course-rest-get")
    public Optional<Course> searchForRest(String q){
        final String keyword = q.toLowerCase();
        List<Course> results = courses.stream().filter((Course c) -> c.getCoursePK().getCourseCode().toLowerCase().contains(keyword) || c.getName().toLowerCase().contains(keyword)).collect(Collectors.toList());
        return results.size() >= 1 ? Optional.of(results.get(0)) : Optional.empty();
    }


}

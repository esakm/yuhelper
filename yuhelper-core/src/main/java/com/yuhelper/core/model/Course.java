package com.yuhelper.core.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @EmbeddedId
    private CoursePK coursePK;

    private String name;
    private String faculty;
    private String department;
    private String description;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy(value = "section ASC")
    private Set<CourseOffering> courseOfferings = new LinkedHashSet<>();

    public Course() {

    }

    public CoursePK getCoursePK() {
        return coursePK;
    }

    public void setCoursePK(CoursePK coursePK) {
        this.coursePK = coursePK;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void setCourseOfferings(Set<CourseOffering> courseOfferings) {
        this.courseOfferings = courseOfferings;
    }

    public float getCredits() {
        return coursePK.getCredits();
    }

    public void setCredits(float credits) {
        coursePK.setCredits(credits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Course) {
            return ((Course) o).getCoursePK().equals(coursePK);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(coursePK);
    }
}

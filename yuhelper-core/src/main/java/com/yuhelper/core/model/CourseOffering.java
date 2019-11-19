package com.yuhelper.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "course_offerings")
public class CourseOffering {

    @Id
    @Column(name = "course_offering_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger courseOfferingId;

    private String term;
    private String director;
    private String section;
    private String session;

    @OneToMany(mappedBy = "courseOffering", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy(value = "id ASC")
    private Set<CourseLecture> courseLectures = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "course", referencedColumnName = "course_code"),
            @JoinColumn(name = "course_credits", referencedColumnName = "credits")
    })
    @JsonIgnore
    private Course course;

    public CourseOffering() {

    }

    public BigInteger getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(BigInteger courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String semester) {
        this.term = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Set<CourseLecture> getCourseLectures() {
        return courseLectures;
    }

    public void setCourseLectures(Set<CourseLecture> courseLectures) {
        this.courseLectures = courseLectures;
    }

    public String getSession() {
        return session;
    }
                                                                                    
    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseOfferingId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CourseOffering) {
            return ((CourseOffering) o).getCourseOfferingId().equals(courseOfferingId);
        } else {
            return false;
        }
    }
}

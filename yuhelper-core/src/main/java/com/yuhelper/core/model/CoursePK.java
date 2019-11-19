package com.yuhelper.core.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CoursePK implements Serializable {

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "credits")
    private float credits;


    public CoursePK() {

    }

    public CoursePK(String courseCode, float credits) {
        this.courseCode = courseCode;
        this.credits = credits;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof CoursePK) {
            return Float.floatToIntBits(((CoursePK) o).credits) == Float.floatToIntBits(credits) && courseCode.equals(((CoursePK) o).courseCode);

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, credits);
    }

}

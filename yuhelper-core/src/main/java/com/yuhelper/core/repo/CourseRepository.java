package com.yuhelper.core.repo;

import com.yuhelper.core.model.Course;
import com.yuhelper.core.model.CoursePK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CustomJPARepository<Course, CoursePK> {

    @Query("SELECT cO FROM Course cO WHERE cO.coursePK.courseCode LIKE %:keyword% OR " +
            "cO.name LIKE %:keyword% ORDER BY  cO.coursePK.courseCode")
    public List<Course> searchForAutoComplete(@Param(value = "keyword") String keyword, Pageable pageable);

    @Query("SELECT cO FROM Course cO WHERE CONCAT(cO.coursePK.courseCode, ' ', cO.coursePK.credits) LIKE %:keyword%")
    public Course searchByCourseCodeAndCredits(@Param(value = "keyword") String course);

}

package com.yuhelper.core.repo;

import com.yuhelper.core.model.CourseOffering;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CourseOfferingRepository extends CustomJPARepository<CourseOffering, BigInteger> {

    @Query("SELECT cO FROM CourseOffering cO WHERE cO.course.name LIKE %:keyword% OR " +
            "cO.course.coursePK.courseCode LIKE %:keyword% ORDER BY cO.course.coursePK.courseCode ")
    public List<CourseOffering> searchByName(@Param(value = "keyword") String keyword, Pageable pageable);

}

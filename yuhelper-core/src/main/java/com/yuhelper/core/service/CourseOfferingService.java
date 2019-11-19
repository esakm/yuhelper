package com.yuhelper.core.service;

import com.yuhelper.core.model.CourseOffering;
import com.yuhelper.core.repo.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CourseOfferingService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

}

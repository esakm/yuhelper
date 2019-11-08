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

    public List<CourseOffering> searchByName(String keyword, Pageable page){
        return courseOfferingRepository.searchByName(keyword, page);
    }

    public LinkedList<CourseOffering> searchForAutoComplete(String keyword){
        List<CourseOffering> list = courseOfferingRepository.searchByName(keyword, PageRequest.of(0, 10));
        LinkedList<CourseOffering> ll = new LinkedList<>();
        list.forEach(co -> ll.add(co));
        return ll;
    }

}

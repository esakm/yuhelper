package com.yuhelper.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomJPARepository<T, ID extends Serializable> extends JpaRepository<T, ID>, PagingAndSortingRepository<T, ID> {
    void refresh(T t);

    void persist(T t);

    void merge(T t);

    void remove(T t);

    T update(T t);

    void detach(T t);
}

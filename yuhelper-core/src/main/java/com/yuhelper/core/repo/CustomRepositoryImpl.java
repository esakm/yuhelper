package com.yuhelper.core.repo;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
        CustomJPARepository<T, ID> {

    @PersistenceContext
    private final EntityManager em;


    public CustomRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    public CustomRepositoryImpl(Class<T> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
        this.em = entityManager;
    }

    @Override
    @Transactional
    public void refresh(T t) {
        em.refresh(t);
    }

    @Override
    @Transactional
    public void merge(T t) {
        em.merge(t);
    }

    @Override
    @Transactional
    public T update(T t) {
        return em.merge(t);
    }

    @Override
    @Transactional
    public void remove(T t) {
        em.remove(t);
    }

    @Override
    @Transactional
    public void persist(T t) {
        em.persist(t);
    }

    @Override
    @Transactional
    public void detach(T t) {
        em.detach(t);
    }
}

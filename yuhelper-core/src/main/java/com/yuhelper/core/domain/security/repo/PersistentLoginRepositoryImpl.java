package com.yuhelper.core.domain.security.repo;


import com.yuhelper.core.domain.security.model.PersistentLogin;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;


public class PersistentLoginRepositoryImpl extends SimpleJpaRepository<PersistentLogin, String> implements PersistentTokenRepository, PersistentLoginRepository {

    @PersistenceContext
    EntityManager em;

    public PersistentLoginRepositoryImpl(EntityManager entityManager) {
        super(PersistentLogin.class, entityManager);
    }


    @Override
    @Transactional
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin login = new PersistentLogin();
        login.setUsername(token.getUsername());
        login.setSeries(token.getSeries());
        login.setToken(token.getTokenValue());
        login.setLastUsed(token.getDate());
        saveAndFlush(login);
    }

    @Override
    @Transactional
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<PersistentLogin> login = findById(seriesId);
        if (login.isPresent()) {
            return new PersistentRememberMeToken(login.get().getUsername(),
                    login.get().getSeries(), login.get().getToken(), login.get().getLastUsed());
        }

        return null;
    }

    @Override
    @Transactional
    public void removeUserTokens(String username) {
        Query q = em.createQuery("DELETE FROM PersistentLogin pL WHERE pL.username = :keyword");
        q.setParameter("keyword", username);
        q.executeUpdate();
        em.flush();
    }

    @Override
    @Transactional
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogin login = findById(series).get();
        login.setToken(tokenValue);
        login.setLastUsed(lastUsed);
        update(login);
        em.flush();
    }

    @Override
    @Transactional
    public void refresh(PersistentLogin t) {
        em.refresh(t);
    }

    @Override
    @Transactional
    public void merge(PersistentLogin t) {
        em.merge(t);
    }

    @Override
    @Transactional
    public PersistentLogin update(PersistentLogin t) {
        return em.merge(t);
    }

    @Override
    @Transactional
    public void remove(PersistentLogin t) {
        em.remove(t);
    }

    @Override
    @Transactional
    public void persist(PersistentLogin t) {
        em.persist(t);
    }

    @Override
    @Transactional
    public void detach(PersistentLogin t) {
        em.detach(t);
    }

}

package com.yuhelper.core.domain.security.repo;

import com.yuhelper.core.domain.security.model.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String>, PersistentTokenRepository {

    void refresh(PersistentLogin t);

    void persist(PersistentLogin t);

    void merge(PersistentLogin t);

    void remove(PersistentLogin t);

    PersistentLogin update(PersistentLogin t);

    void detach(PersistentLogin t);
}

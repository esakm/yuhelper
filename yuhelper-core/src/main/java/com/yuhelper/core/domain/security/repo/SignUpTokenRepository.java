package com.yuhelper.core.domain.security.repo;

import com.yuhelper.core.domain.security.model.SignUpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignUpTokenRepository extends JpaRepository<SignUpToken, Integer> {

    @Query(value = "SELECT sT FROM SignUpToken sT WHERE (sT.token = ?1)")
    public Optional<SignUpToken> findByToken(String token);



}

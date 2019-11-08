package com.yuhelper.core.repo;

import com.yuhelper.core.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;

@Repository
public interface UserRepository extends CustomJPARepository<User, BigInteger> {
    @Query(value = "SELECT u FROM User u WHERE u.username = ?1")
    User getUserByUsername(String q);

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1")
    User getUserByEmail(String q);

    @Query(value = "DELETE u FROM user u INNER JOIN signup_tokens sT ON id = user_id WHERE sT.expiry_time < ?1", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteExpiredUsers(String currentDate);
}

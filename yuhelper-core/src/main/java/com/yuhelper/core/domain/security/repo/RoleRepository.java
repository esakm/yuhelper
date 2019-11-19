package com.yuhelper.core.domain.security.repo;

import com.yuhelper.core.domain.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "SELECT r FROM Role r WHERE (r.name = ?1)")
    public Role findByName(String name);
}

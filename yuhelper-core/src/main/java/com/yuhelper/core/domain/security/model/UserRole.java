package com.yuhelper.core.domain.security.model;

import com.yuhelper.core.model.User;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger userRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole() {

    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public BigInteger getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(BigInteger userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode(){
        return Objects.hash(role);
    }


}

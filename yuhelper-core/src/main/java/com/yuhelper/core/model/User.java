package com.yuhelper.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuhelper.core.domain.security.model.Authority;
import com.yuhelper.core.domain.security.model.SignUpToken;
import com.yuhelper.core.domain.security.model.UserRole;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;

@Entity
@Transactional
@Configurable
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @JsonIgnore
    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "username")
    private String username;
    private String email;
    private String passwordHash;


    // TODO IMPLEMENT SALT GENERATION SERVER SIDE FOR NEW USERS
    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "create_time", insertable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date creationDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserRole> userRoles = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private SignUpToken signUpToken;


    public void setId(BigInteger id) {
        this.id = id;
    }

    public User() {

    }


    /**
     * This constructor is to be used when creating new users.
     *
     * @param username
     * @param email
     */
    public User(String username, String email, String password, String salt) {
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.salt = salt;
        this.enabled = false;
    }

    public User(String username) {
        this.username = username;
    }

    public BigInteger getId() {
        return id;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    @JsonIgnore
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreationTime() {
        return creationDate;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    @JsonProperty
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonProperty
    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole userRole){
        this.userRoles.add(userRole);
    }

    public SignUpToken getSignUpToken() {
        return signUpToken;
    }

    public void setSignUpToken(SignUpToken signUpToken) {
        this.signUpToken = signUpToken;
    }

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    public String getPassword() {
        return getPasswordHash();
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }
        if (o instanceof User) {
            return ((User) o).getId().equals(id);
        } else {
            return false;
        }
    }


}


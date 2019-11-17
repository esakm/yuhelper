package com.yuhelper.core.domain.security.model;

import com.yuhelper.core.model.User;
import com.yuhelper.core.utils.DateFactory;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "signup_tokens")
public class SignUpToken {


    @Column(name = "token_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger tokenId;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne
    private User user;

    @Column(nullable = false)
    private String token;

    @Column(name = "token_create_time", columnDefinition = "DATETIME", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date startTime;

    @Column(name = "expiry_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryTime;


    public SignUpToken() {

    }

    public SignUpToken(User user, String token) {
        this.user = user;
        this.token = token;
        expiryTime = DateFactory.getShortTokenExpiryDate();
    }

    public BigInteger getTokenId() {
        return tokenId;
    }

    public void setTokenId(BigInteger tokenId) {
        this.tokenId = tokenId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o instanceof SignUpToken) {
            return ((SignUpToken) o).getTokenId().equals(tokenId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId);
    }

}

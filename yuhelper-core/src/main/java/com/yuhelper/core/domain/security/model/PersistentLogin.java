package com.yuhelper.core.domain.security.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="persistent_logins")
public class PersistentLogin {

    private String username;

    private String token;
    @Id
    private String series;

    @Column(name = "last_used")
    private Date lastUsed;

    public PersistentLogin(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }else if(o instanceof PersistentLogin){
            return ((PersistentLogin) o).getUsername().equals(username);
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(username);
    }
}

package com.myf.security.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by myf on 2017/10/20.
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins implements Serializable {
    @Id
    @Column(name = "series",length = 64,nullable = false)
    private String series;
    @Column(name = "username",length = 64,nullable = false)
    private String username;
    @Column(name = "token",length = 64,nullable = false)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used",nullable = false)
    private Date last_used;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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

    public Date getLast_used() {
        return last_used;
    }

    public void setLast_used(Date last_used) {
        this.last_used = last_used;
    }
}

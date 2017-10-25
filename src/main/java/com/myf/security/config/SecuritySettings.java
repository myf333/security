package com.myf.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by myf on 2017/10/20.
 */
@ConfigurationProperties(prefix = "security.setting")
public class SecuritySettings {
    private String logoutsuccessurl = "/logout";
    private String permitall = "/api";
    private String deniedpage = "/deny";
    private String userroles = "";

    public String getLogoutsuccessurl() {
        return logoutsuccessurl;
    }

    public void setLogoutsuccessurl(String logoutsuccessurl) {
        this.logoutsuccessurl = logoutsuccessurl;
    }

    public String getPermitall() {
        return permitall;
    }

    public void setPermitall(String permitall) {
        this.permitall = permitall;
    }

    public String getDeniedpage() {
        return deniedpage;
    }

    public void setDeniedpage(String deniedpage) {
        this.deniedpage = deniedpage;
    }

    public String getUserroles() {
        return userroles;
    }

    public void setUserroles(String userroles) {
        this.userroles = userroles;
    }
}

package vn.kms.launch.contactmgr.domain.security;

import java.util.Date;

import vn.kms.launch.contactmgr.domain.user.User;

public class UserInfo {
    private String username;
    private String email;
    private String role;
    private Date expiredDate;
    private boolean active;
    
    public UserInfo(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.expiredDate = user.getExpiredDate();
        this.active = user.isActive();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}

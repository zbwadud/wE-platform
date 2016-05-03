/*
****
 */
package com.wentity.moversui.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */

public class User  implements Serializable{

        
    private Integer userID;    
    private String password;
    private String email;   
    private boolean active;    
    private Date lastLogin;    
    private Date createdDate;
    
    private List<User> userCollection;

    @JsonIgnore
    public List<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(List<User> userCollection) {
        this.userCollection = userCollection;
    }

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public User(Integer userID, String password, String email, boolean active) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.active = active;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "com.wentity.moversui.domain.Users[ userID=" + userID + " ]";
    }
    
}

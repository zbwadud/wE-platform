/*
****
 */
package com.wentity.ui.model;

import java.io.Serializable;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
//@ManagedBean(name = "userBean", eager = false)
//@RequestScoped
public class UserBean implements Serializable {
    
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBean{" + "userName=" + userName + ", password=" + password + '}';
    }
    
    
}

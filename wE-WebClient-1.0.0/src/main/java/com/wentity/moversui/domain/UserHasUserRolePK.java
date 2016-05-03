/*
****
 */
package com.wentity.moversui.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
@Embeddable
public class UserHasUserRolePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "UserID")
    private int userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RoleID")
    private int roleID;

    public UserHasUserRolePK() {
    }

    public UserHasUserRolePK(int userID, int roleID) {
        this.userID = userID;
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userID;
        hash += (int) roleID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasUserRolePK)) {
            return false;
        }
        UserHasUserRolePK other = (UserHasUserRolePK) object;
        if (this.userID != other.userID) {
            return false;
        }
        if (this.roleID != other.roleID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wentity.moversui.domain.UserHasUserRolePK[ userID=" + userID + ", roleID=" + roleID + " ]";
    }
    
}

/*
****
 */
package com.wentity.usermanagement.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
@Entity
@Table(name = "user_has_user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserHasUserRole.findAll", query = "SELECT u FROM UserHasUserRole u"),
    @NamedQuery(name = "UserHasUserRole.findByUserID", query = "SELECT u FROM UserHasUserRole u WHERE u.userHasUserRolePK.userID = :userID"),
    @NamedQuery(name = "UserHasUserRole.findByRoleID", query = "SELECT u FROM UserHasUserRole u WHERE u.userHasUserRolePK.roleID = :roleID"),
    @NamedQuery(name = "UserHasUserRole.findByCreatedByUserID", query = "SELECT u FROM UserHasUserRole u WHERE u.createdByUserID = :createdByUserID"),
    @NamedQuery(name = "UserHasUserRole.findByCreatedDate", query = "SELECT u FROM UserHasUserRole u WHERE u.createdDate = :createdDate")})
public class UserHasUserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserHasUserRolePK userHasUserRolePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreatedByUserID")
    private int createdByUserID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserRole userRole;
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public UserHasUserRole() {
    }

    public UserHasUserRole(UserHasUserRolePK userHasUserRolePK) {
        this.userHasUserRolePK = userHasUserRolePK;
    }

    public UserHasUserRole(UserHasUserRolePK userHasUserRolePK, int createdByUserID, Date createdDate) {
        this.userHasUserRolePK = userHasUserRolePK;
        this.createdByUserID = createdByUserID;
        this.createdDate = createdDate;
    }

    public UserHasUserRole(int userID, int roleID) {
        this.userHasUserRolePK = new UserHasUserRolePK(userID, roleID);
    }

    public UserHasUserRolePK getUserHasUserRolePK() {
        return userHasUserRolePK;
    }

    public void setUserHasUserRolePK(UserHasUserRolePK userHasUserRolePK) {
        this.userHasUserRolePK = userHasUserRolePK;
    }

    public int getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCreatedByUserID(int createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userHasUserRolePK != null ? userHasUserRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserHasUserRole)) {
            return false;
        }
        UserHasUserRole other = (UserHasUserRole) object;
        if ((this.userHasUserRolePK == null && other.userHasUserRolePK != null) || (this.userHasUserRolePK != null && !this.userHasUserRolePK.equals(other.userHasUserRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wentity.moversui.domain.UserHasUserRole[ userHasUserRolePK=" + userHasUserRolePK + " ]";
    }
    
}

/*
****
 */
package com.wentity.moversui.ejb;

import com.wentity.moversui.domain.UserHasUserRole;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
@Stateless
public class UserHasUserRoleFacade extends AbstractFacade<UserHasUserRole> {

    @PersistenceContext(unitName = "MoversUI-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserHasUserRoleFacade() {
        super(UserHasUserRole.class);
    }
    
}

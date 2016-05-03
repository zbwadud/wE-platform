/*
****
 */
package com.wentity.moversui.services;

import com.wentity.moversui.domain.UserHasUserRole;
import com.wentity.moversui.domain.UserHasUserRolePK;
import com.wentity.moversui.ejb.AbstractFacade;
import com.wentity.moversui.ejb.UserHasUserRoleFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
//@Stateless
@Path("com.wentity.moversui.domain.userhasuserrole")
public class UserHasUserRoleFacadeREST {
    /*
    @PersistenceContext(unitName = "MoversUI-PU")
    */
    @EJB
    private UserHasUserRoleFacade ejbFacade;
    
    private UserHasUserRoleFacade getFacade() {
        return ejbFacade;
    }
    
    private UserHasUserRolePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;userID=userIDValue;roleID=roleIDValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.wentity.moversui.domain.UserHasUserRolePK key = new com.wentity.moversui.domain.UserHasUserRolePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> userID = map.get("userID");
        if (userID != null && !userID.isEmpty()) {
            key.setUserID(new java.lang.Integer(userID.get(0)));
        }
        java.util.List<String> roleID = map.get("roleID");
        if (roleID != null && !roleID.isEmpty()) {
            key.setRoleID(new java.lang.Integer(roleID.get(0)));
        }
        return key;
    }

    public UserHasUserRoleFacadeREST() {
        
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserHasUserRole entity) {
        ejbFacade.edit(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, UserHasUserRole entity) {
       ejbFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.wentity.moversui.domain.UserHasUserRolePK key = getPrimaryKey(id);
        ejbFacade.remove(ejbFacade.find(key));      
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserHasUserRole find(@PathParam("id") PathSegment id) {
        com.wentity.moversui.domain.UserHasUserRolePK key = getPrimaryKey(id);
        return ejbFacade.find(key);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserHasUserRole> findAll() {
        return ejbFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserHasUserRole> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return ejbFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(ejbFacade.count());
    }
        
}

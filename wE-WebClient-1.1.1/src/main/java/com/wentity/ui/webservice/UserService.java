/*
****
 */
package com.wentity.ui.webservice;

import com.wentity.ui.model.UserBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
@Path("users")
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
  
    @PUT
    @Path("userLogin")
    @Produces({MediaType.APPLICATION_JSON})
    public String userLogin(UserBean user) {
        log.info("user login "+user.toString());
        return "success";
    }
    
    @PUT
    @Path("backendLogin")
    @Produces({MediaType.APPLICATION_JSON})
    public String backendLogin(UserBean user) {
        log.info("backend login "+user.toString());
        return "success";
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void userRegister(UserBean user) {
        log.info("userRegister");
    }
    /*
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void adminRegister(UserBean user) {
        log.info("adminRegister");
    }
    */
    /*
    @GET
    @Path("{email}")
    @Produces({MediaType.TEXT_PLAIN})
    public boolean findEmail(String email) {
    log.info("findEmail");
    
    return true;
    }
    
    @GET
    @Path("{cellNumber}")
    @Produces({MediaType.TEXT_PLAIN})
    public boolean findCellNumber(String cellNumber) {
    log.info("findCellNumber");
    
    return true;
    }
*/
}

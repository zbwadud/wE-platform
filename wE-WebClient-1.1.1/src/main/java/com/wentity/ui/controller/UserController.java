/*
****
 */
package com.wentity.ui.controller;

import com.wentity.ui.model.UserBean;
import com.wentity.ui.webservice.ServiceClient;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserBean userBean;
    private ServiceClient serviceClient = new ServiceClient();
    
    public UserController() {
        
        if(userBean == null){
            userBean = new UserBean();
        }
    }
    public String userLoginSuccess(){
        
        return "template";
    }
    
    public String adminLoginSuccess(){
        
        return "template";
    }
            
            
    public String userLogin(){
        //userService.create(userBean);
       int response = serviceClient.userLogin(userBean);
       log.info("Service Response :"+response);
        serviceClient.close();
        return userLoginSuccess();
    }
    
    public void userRegister(){
    
    }
    
    public void userIdExist(){
    
    }
    
    private String passwordGenerator(){
        
        return "";
    }
    
    public String adminLogin(){
        //userService.create(userBean);
       //int response = serviceClient.login(userBean);
       //log.info("Service Response :"+response);
        //serviceClient.close();
        return adminLoginSuccess();
    }
    
    public UserBean getSelected() {
        if (userBean == null) {
            userBean = new UserBean();
        }
        return userBean;
    }
    
}

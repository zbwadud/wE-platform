/*
****
 */
package com.wentity.ui.controller;

import com.wentity.ui.model.UserBean;
import com.wentity.ui.webservice.ServiceClient;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
@ManagedBean(name = "userController")
@SessionScoped
public class UserController implements Serializable {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserBean userBean;
    private ServiceClient serviceClient;
    
    public UserController() {
        
        if(userBean == null){
            userBean = new UserBean();
        }
        
        if(serviceClient == null){
            serviceClient = new ServiceClient();
        }
    }
    public String userLoginSuccess(){
        
        return "user";
    }
    
    public String adminLoginSuccess(){
        
        return "user";
    }
            
            
    public String userLogin() {
        //userService.create(userBean);
        log.info("userBean--->" + userBean);
        int response = serviceClient.userLogin(userBean);
        log.info("userLogin-->Service Response :" + response);
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
    
    public String logout(){
    
        return "/index";
    }
    
    public String userBackEndLogin(){
        //userService.create(userBean);
       log.info("userBean--->" + userBean);
       int response = serviceClient.backendLogin(userBean);
       log.info("userBackEndLogin --> Service Response :"+response);
        serviceClient.close();
        return adminLoginSuccess();
    }
    
    public UserBean getSelected() {
        if (userBean == null) {
            userBean = new UserBean();
        }
        return userBean;
    }
    
}

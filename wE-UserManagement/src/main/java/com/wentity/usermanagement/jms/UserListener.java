/*
****
 */
package com.wentity.usermanagement.jms;

import com.wentity.usermanagement.domain.Users;
import com.wentity.usermanagement.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */

@Component
public class UserListener {
    private static final Logger log = LoggerFactory.getLogger(UserListener.class);
    private final UserService userService;
    @Autowired
    ConfigurableApplicationContext context;
    
    @Autowired
    public UserListener(UserService userService) {
        this.userService = userService;
    }
    
    //@JmsListener(destination = "UsersManager.Create")
    //@SendTo("users.UserManager")
    public String registerUser(Users user){        
        
        log.info("Users registerd <"+user.toString()+">");
        
        return userService.saveUser(user);
    }
    
    public String editUser(Users user){
        
        return userService.editUser(user);
    }
    
    public List<Users> findRange(Integer fromTo[]){        
        log.info("Range data retuerned-->"+fromTo[0]+"-"+fromTo[1]);
        return userService.findRange(fromTo);
    }
    
    public List<Users> findAll(){        
        log.info("find all");
        return userService.findAll(); 
    }
    
    public String countEntity(){
        log.info("total entity "+userService.countEntity());
        return String.valueOf(userService.countEntity());
    }
    
    public String deleteEntity(Integer id){
        log.info("delete user entity "+id);
        return userService.deleteEntity(id);
    }
    
    public Users findSingle(Integer id){
        log.info("find single entity "+ id);
        return userService.findSingle(id);
    }
        
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wentity.usermanagement.service;


import com.wentity.usermanagement.domain.Users;
import com.wentity.usermanagement.repository.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zaid Wadud
 */
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Users> findAll() {
        
        return userRepository.findAll();
    }

    public String saveUser(Users user) {
        userRepository.save(user);
        return "register_entity_success";
    }
    
    public String editUser(Users user){
        userRepository.save(user);
        return "edit_entity_success";
    }
    
    public String deleteEntity(Integer id){
        userRepository.delete(id);
        return "delete_entity_success";
    }
    
        
    public List<Users> findRange(Integer fromTo []){
        
        return userRepository.findAll();
    }
    
    public long countEntity(){
        
        return userRepository.count();
    }
    
    public Users findSingle(Integer id){
    
        return userRepository.findOne(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wentity.usermanagement.repository;

import com.wentity.usermanagement.domain.Users;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Zaid Wadud
 */
public interface UserRepository extends PagingAndSortingRepository<Users, Integer> {
    
   @Override
   public List <Users> findAll();

    @Override
    public Page<Users> findAll(Pageable pgbl);  
   
   
}

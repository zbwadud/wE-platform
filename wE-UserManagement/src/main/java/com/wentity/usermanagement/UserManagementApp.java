/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wentity.usermanagement;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.jms.annotation.EnableJms;

/**
 *
 * @author Zaid Wadud
 */
@SpringBootApplication
public class UserManagementApp implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(UserManagementApp.class);
    @Autowired
    private ApplicationContext context;
    
    public static void main(String[] args){
        //log.info("User Management Application Starting....");
        SpringApplication app = new SpringApplication(UserManagementApp.class);
        app.setWebEnvironment(false);
        app.run(args);
        
    }

    @Override
    public void run(String... strings) throws Exception {
        
       DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        SpringXmlConfigurationBuilder configBuilder = null;
        try {
            StaticApplicationContext staticApplicationContext = new StaticApplicationContext(context);
            configBuilder = new SpringXmlConfigurationBuilder("mule-config.xml");
            staticApplicationContext.refresh();
            configBuilder.setParentContext(staticApplicationContext);
            MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
            muleContext.start();
            log.info("Started Mule!"+ muleContext.getClient().toString());
        } catch (BeansException | IllegalStateException | MuleException e) {
            log.warn(e.getMessage());
        }
    }

}

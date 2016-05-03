/*
****
 */
package com.wentity.moversui.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wentity.moversui.domain.Users;
import com.wentity.moversui.ejb.UsersFacade;
import com.wentity.moversui.jms.UserMessagePublisher;
import com.wentity.moversui.jms.UserMessagesReceiver;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
//@Stateless
@Path("com.wentity.moversui.domain.users")
public class UsersFacadeREST{
    /*
    @PersistenceContext(unitName = "MoversUI-PU")
    */
    @EJB
    private UsersFacade ejbFacade;
    private UserMessagePublisher publisher;
    private UserMessagesReceiver consumer;
    private ObjectMapper mapper;
    public UserMessagePublisher getPublisher() {
        return publisher;
    }

    public void setPublisher(UserMessagePublisher publisher) {
        this.publisher = publisher;
    }

    public UserMessagesReceiver getConsumer() {
        return consumer;
    }

    public void setConsumer(UserMessagesReceiver consumer) {
        this.consumer = consumer;
    }
    
    private UsersFacade getFacade() {
        return ejbFacade;
    }
    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    public UsersFacadeREST() {
        this.publisher = new UserMessagePublisher();
        this.consumer  = new UserMessagesReceiver();
        this.mapper = new ObjectMapper();
        
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Object entity) {
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,"create_entity_fired");          
            getPublisher().sendJMSMessage(entity, "queue.UserManager.Operation", "createEntity");
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Object entity) {
        String info = null;
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,"edit_entity_fired");          
            getPublisher().sendJMSMessage(entity, "queue.UserManager.Operation", "editEntity");
            getConsumer().setEndpointToListen("queue.UserManager.Notification","editEntityInfo");
            info = consumer.getMessage().getText();
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,info);
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") String id) {
        String info = null;
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,"remove_entity_fired "+id);          
            getPublisher().sendJMSMessage(id, "queue.UserManager.Operation", "deleteEntity");
            getConsumer().setEndpointToListen("queue.UserManager.Notification","deleteEntityInfo");
            info = consumer.getMessage().getText();
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,info);
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String find(@PathParam("id") Integer id) {
        String jsonObject = null;
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,"find_single_fired");          
            getPublisher().sendJMSMessage(id, "queue.UserManager.Notification", "findSingleInfo");
            getConsumer().setEndpointToListen("queue.UserManager.Operation","findSingleEntity");
            jsonObject = consumer.getMessage().getText();
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO,jsonObject);
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String findAll() {
        String jsonObject = null;
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO, "find_all_event_fired");
            getPublisher().sendJMSMessage("findAll_event_fired", "queue.UserManager.Notification", "findAllInfo");
            getConsumer().setEndpointToListen("queue.UserManager.Operation", "findAllEntity");
            jsonObject = consumer.getMessage().getText();
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        String jsonObject = null;
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO, "find_range_event_fired");
            getPublisher().sendJMSMessage(getMapper().writeValueAsString(new int[] {from, to}), "queue.UserManager.Notification", "findRangeInfo");
            getConsumer().setEndpointToListen("queue.UserManager.Operation", "findRangeList");
            jsonObject = consumer.getMessage().getText();
        } catch (JMSException | NamingException | JsonProcessingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObject;//ejbFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        String count = "0";
        try {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.INFO, "count_event_fired");            
            getPublisher().sendJMSMessage("count_event", "queue.UserManager.Notification", "countInfo");
            getConsumer().setEndpointToListen("queue.UserManager.Operation","countEntity");
            count = consumer.getMessage().getText();
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(UsersFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;//String.valueOf(ejbFacade.count());
    }    
    
}

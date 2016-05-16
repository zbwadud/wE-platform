/*
****
 */
package com.wentity.ui.webservice;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jersey REST client generated for REST resource:UserService [userService]<br>
 * USAGE:
 * <pre>
 *        ServiceClient client = new ServiceClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
public class ServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ServiceClient.class);
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8084/webclient/webresources";

    public ServiceClient() {
        log.info("In webservice client "+BASE_URI);
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("users");
    }
    /*
    public ServiceClient(String username, String password) {
        this();
        setUsernamePassword(username, password);
    }java.text.MessageFormat.format("{0}", new Object[]{id})
    */
    public int userLogin(Object requestEntity) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("userLogin");
        Response response = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(Entity.json(requestEntity));
        
        return response.getStatus();
        
        //return webTarget.path("login").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get().getStatus();
    }
    
    public int backendLogin(Object requestEntity) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("userLogin");
        Response response = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(Entity.json(requestEntity));
        
        return response.getStatus();
        
        //return webTarget.path("login").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get().getStatus();
    }   
    

    public void close() {
        client.close();
    }
    
    /*
    public final void setUsernamePassword(String username, String password) {
        //webTarget.register(HttpBasicAuthFilter(username, password));
    }
    */
}

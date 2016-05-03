/*
****
 */
package com.wentity.moversui.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:UsersFacadeREST
 * [com.wentity.moversui.domain.users]<br>
 * USAGE:
 * <pre>
 *        UserClient client = new UserClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 */
public class UserClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:23485/MoversUI/webresources";

    public UserClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("com.wentity.moversui.domain.users");
    }
    /*
    public UserClient(String username, String password) {
        this();
        setUsernamePassword(username, password);
    }
    */
    public String countREST() throws ClientErrorException {
        System.out.println("User Client countREST Exicuting....");
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
        System.out.println("User Client edit_JSON Exicuting....");
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).
                request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
        System.out.println("User Client find_JSON Exicuting....");
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException {
        System.out.println("User Client findRange_JSON Exicuting....");
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void create_JSON(Object requestEntity) throws ClientErrorException {
        System.out.println("User Client create_JSON Exicuting....");
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException {
        System.out.println("User Client findAll_JSON Exicuting....");
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        System.out.println("User Client remove Exicuting....");
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }
    /*
    public final void setUsernamePassword(String username, String password) {
        webTarget.register(new org.glassfish.jersey.client.filter.HttpBasicAuthFilter(username, password));
    }
    */
    
    
}

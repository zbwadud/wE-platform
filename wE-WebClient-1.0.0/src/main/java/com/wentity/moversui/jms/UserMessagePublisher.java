/*
****
 */
package com.wentity.moversui.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 * 
 */
public class UserMessagePublisher{

    public UserMessagePublisher() {
        Logger.getLogger(UserMessagePublisher.class.getName()).log(Level.INFO, "Message Publisher is called");
    }
    
    
    private Message createJMSMessage(Session session, Object messageData, String qSelector) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setObjectProperty("msgFilter", qSelector);
        tm.setText(messageData.toString());
        return tm;
    }
    
    public void sendJMSMessage(Object messageData, String endpoint, String qSelector) throws JMSException, NamingException {
        
        Logger.getLogger(UserMessagePublisher.class.getName()).log(Level.INFO, "Message will publish : "+messageData.toString());
        Context jndi = new InitialContext();
        ConnectionFactory cf = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);//(ConnectionFactory) jndi.lookup("java:comp/env/queueConnectionFactory");
        Connection conn = null;
        Session s = null;
        try {
            conn = cf.createConnection();
            conn.start();
            s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) jndi.lookup("java:comp/env/"+endpoint);
            MessageProducer mp = s.createProducer(destination);
            mp.send(createJMSMessage(s, messageData, qSelector));
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (conn != null) {
                conn.close();
                //conn.stop();
            }
        }
    }
    /* public void sendJMSMessage(Object messageData, String endpoint) throws JMSException, NamingException {
    
    Logger.getLogger(UserMessagePublisher.class.getName()).log(Level.INFO, "Message will publish : "+messageData.toString());
    Context jndi = new InitialContext();
    ConnectionFactory cf = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);//(ConnectionFactory) jndi.lookup("java:comp/env/queueConnectionFactory");
    Connection conn = null;
    Session s = null;
    try {
    conn = cf.createConnection();
    conn.start();
    s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
    Destination destination = (Destination) jndi.lookup("java:comp/env/"+endpoint);//java:comp/env/jms/
    MessageProducer mp = s.createProducer(destination);
    mp.send(createJMSMessage(s, messageData,""));
    } finally {
    if (s != null) {
    try {
    s.close();
    } catch (JMSException e) {
    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
    }
    }
    if (conn != null) {
    conn.close();
    //conn.stop();
    }
    }
    }*/
    
}

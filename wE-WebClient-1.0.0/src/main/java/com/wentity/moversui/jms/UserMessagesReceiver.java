/*
****
 */
package com.wentity.moversui.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


/**
 *
 * @author Zaid Wadud <Zaid Wadud at wEntity System Ltd.>
 *
 */
public class UserMessagesReceiver {
    private TextMessage message;

    public TextMessage getMessage() {
        
        return message;
    }

    public void setMessage(TextMessage message) {
        this.message = message;
    }
    
    
    public UserMessagesReceiver(){
    
    
    }
    public void setEndpointToListen(String endpoint, String qSelector) {
        try {
            Logger.getLogger(UserMessagesReceiver.class.getName()).log(Level.INFO, "Message Receiver is called");
            receivedMessage(endpoint, qSelector);
        } catch (JMSException ex) {
            Logger.getLogger(UserMessagesReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(UserMessagesReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       
    private void receivedMessage(String endpoint, String qSelector) throws JMSException, NamingException {
        Context jndi = new InitialContext();
        ConnectionFactory cf = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);//(ConnectionFactory) jndi.lookup("java:comp/env/queueConnectionFactory");//java:comp/env/queueConnectionFactory     java:comp/env/java:comp/DefaultJMSConnectionFactory
        Connection conn = null;
        Session s = null;
        try {
            conn = cf.createConnection();
            conn.start();
            s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) jndi.lookup("java:comp/env/"+endpoint);//java:comp/env/jms/UsersMang.Count
            
            //MessageProducer mp = s.createProducer(destination);
            MessageConsumer consumer = s.createConsumer(destination,"msgFilter = "+"'"+qSelector+"'");
            //ogger.getLogger(MessagesReceiver.class.getName()).log(Level.INFO, "message : ",consumer.receive());
            Message m = consumer.receive();
            TextMessage textMsg = null;
            if (m instanceof TextMessage) {
            textMsg = (TextMessage) m;
            System.out.println("Reading message: " + textMsg.getText());
            } else {
                System.out.println("message: is not text message" + textMsg.getText());
            }
            setMessage(textMsg);
            
            //send(getMessage(s, messageData));
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
    
    
}

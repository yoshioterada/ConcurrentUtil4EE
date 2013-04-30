package jp.co.oracle.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MailAddressRegisterEJB {
//    @javax.jms.JMSConnectionFactory(value = "java:comp/DefaultJMSConnectionFactory")
    @Resource(mappedName = "java:comp/DefaultJMSConnectionFactory")
    ConnectionFactory conn;

    @Resource(mappedName = "jms/mailRegistQueue")
    Queue queue;
        
    public void registEmailAddress(String address){
        try(JMSContext context = conn.createContext()){
            context.createProducer().send(queue, address);
        }
    }
}

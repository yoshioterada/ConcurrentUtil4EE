package jp.co.oracle.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jp.co.oracle.dao.PersonEntity;

@Stateless
public class MailRegJMSSendQueueEJB {
//    @javax.jms.JMSConnectionFactory(value = "java:comp/DefaultJMSConnectionFactory")

    @Resource(mappedName = "java:comp/DefaultJMSConnectionFactory")
    ConnectionFactory conn;
    @Resource(mappedName = "jms/mailRegistQueue")
    Queue queue;
    @PersistenceContext(unitName = "UserRegisterPU")
    EntityManager em;
    static final Logger logger = Logger.getLogger(MailRegJMSSendQueueEJB.class.getPackage().getName());

    public void registEmailAddress(PersonEntity person) {
        logger.log(Level.INFO, person.toString());

        em.persist(person);
        try (JMSContext context = conn.createContext()) {
            context.createProducer().send(queue, person.getAddress());
        }
    }
}

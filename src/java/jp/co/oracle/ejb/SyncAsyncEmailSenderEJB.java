package jp.co.oracle.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jp.co.oracle.dao.PersonEntity;
import jp.co.oracle.mail.MailSender;

@Stateless
public class SyncAsyncEmailSenderEJB {

    @Inject
    MailSender mailsend;
    @PersistenceContext(unitName = "UserRegisterPU")
    EntityManager em;
    private static final Logger logger = Logger.getLogger(SyncAsyncEmailSenderEJB.class.getPackage().getName());

    public void sendMessage(PersonEntity person) {
        em.persist(person);
        mailsend.sendMessage(person.getEmailaddress());
    }

    @Asynchronous
    public void asyncSendMessage(PersonEntity person) {
        em.persist(person);
        mailsend.sendMessage(person.getEmailaddress());
    }
}

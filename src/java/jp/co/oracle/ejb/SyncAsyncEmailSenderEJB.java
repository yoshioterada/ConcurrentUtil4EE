package jp.co.oracle.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jp.co.oracle.cdi.Person;
import jp.co.oracle.dao.PersonEntity;
import jp.co.oracle.mail.MailSender;

@Stateless
public class SyncAsyncEmailSenderEJB {

    @Inject
    MailSender mailsend;
    @PersistenceContext(unitName = "UserRegisterPU")
    EntityManager em;

    public void sendMessage(PersonEntity person) {
        Logger.getLogger(SyncAsyncEmailSenderEJB.class.getName()).log(Level.INFO, person.toString());
        em.persist(person);
        mailsend.sendMessage(person.getEmailaddress());
    }

    @Asynchronous
    public void asyncSendMessage(PersonEntity person) {
        Logger.getLogger(SyncAsyncEmailSenderEJB.class.getName()).log(Level.INFO, person.toString());
        em.persist(person);
        mailsend.sendMessage(person.getEmailaddress());
    }
}

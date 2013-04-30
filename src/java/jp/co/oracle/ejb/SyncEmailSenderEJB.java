package jp.co.oracle.ejb;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import jp.co.oracle.mail.MailSender;

@Stateless
public class SyncEmailSenderEJB {
    
    @Inject
    MailSender mailsend;

    public void sendMessage(String email){
        mailsend.sendMessage(email);
    }
    
    @Asynchronous
    public void asyncSendMessage(String email){
        mailsend.sendMessage(email);
    }
}

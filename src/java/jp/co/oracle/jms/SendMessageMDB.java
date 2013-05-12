package jp.co.oracle.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import jp.co.oracle.mail.MailSender;

@MessageDriven(mappedName = "jms/mailRegistQueue")
public class SendMessageMDB implements MessageListener {
    private static final Logger logger = Logger.getLogger(SendMessageMDB.class.getPackage().getName());

    public SendMessageMDB(){
        
    }
    
    @Inject
    MailSender mailSender;
    
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            mailSender.sendMessage(msg.getText());
        } catch (JMSException jmse) {
            logger.log(Level.SEVERE, "onMessage() failed to send the message ", jmse);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.jms;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import jp.co.oracle.mail.MailSender;

/**
 *
 * @author tyoshio2002
 */
@MessageDriven(mappedName = "jms/mailRegistQueue")
public class SendMessageMDB implements MessageListener {

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
            jmse.printStackTrace();
        }
    }
}

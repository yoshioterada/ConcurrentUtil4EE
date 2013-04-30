package jp.co.oracle.mail;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


@Named(value = "MailSender")
@Dependent
public class MailSender {

    @Resource(name = "mail/MyMailSession")
    Session mailSession;
    static final Logger logger = Logger.getLogger(MailSender.class.getPackage().getName());
    
    public void sendMessage(String emailAddress) {
        logger.log(Level.SEVERE, "Current Thread : {0}", Thread.currentThread().toString());
        Message msg = new MimeMessage(mailSession);
        try {
            Thread.sleep(10000); // 長い処理を実現ここでは 10 秒
            msg = createMessage(emailAddress, msg);
            Transport.send(msg);
        } catch (InterruptedException | MessagingException | UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "Send Message failed : ", e);
        }
    }

    private Message createMessage(String emailAddress, Message msg) throws MessagingException, UnsupportedEncodingException {
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress(
                emailAddress,
                emailAddress));
        msg.setFrom(new InternetAddress(
                "Yoshio.Terada@Oracle.COM",
                "Yoshio Terada"));
        msg.setHeader("X-Mailer", "Yosshi Mailer");
        msg.setSubject(MimeUtility.encodeText("ご登録ありがとうございました", "iso-2022-jp", "B"));

        MimeMultipart contents = new MimeMultipart();
        msg.setContent(contents);
        MimeBodyPart body = new MimeBodyPart();
        body.setContent(emailAddress + "さん、ご登録ありがとうございました。", "text/plain; charset=\"iso-2022-jp\"");
        contents.addBodyPart(body);

        msg.setContent(contents);

        return msg;
    }
}

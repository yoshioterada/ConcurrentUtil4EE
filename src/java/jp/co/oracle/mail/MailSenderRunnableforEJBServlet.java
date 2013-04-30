package jp.co.oracle.mail;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.AsyncContext;

@ManagedBean(value = "MailSenderRunnable")
public class MailSenderRunnableforEJBServlet implements Runnable {
    //EJB からの呼び出しでは @Resource が利用可能 ?!

    @Resource(name = "mail/MyMailSession")
    Session mailSession;
    AsyncContext ac;
    private static final Logger logger = Logger.getLogger(MailSenderRunnableforEJBServlet.class.getPackage().getName());

    public MailSenderRunnableforEJBServlet() {
    }

    public MailSenderRunnableforEJBServlet(AsyncContext ac) {
        this.ac = ac;
        // Servlet からの呼び出しでは InitialContext が利用可能 ?!
        if (mailSession == null) {
            try {
                InitialContext ctxt = new InitialContext();
                mailSession = (Session) ctxt.lookup("java:comp/env/mail/MyMailSession");
            } catch (NamingException ne) {
                logger.log(Level.SEVERE, "Context Look up Failed :", ne);
            }
        }
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "MailSenderRunnableforEJBServlet Async processing Start");
        sendMessage();
        /* もし、非同期の処理側の方でレスポンスを返したい場合
         * Async のコンテキストを渡す事で、下記のように記載可能 
         ac.getResponse().setCharacterEncoding("UTF-8");
         ac.getResponse().setContentType("text/html");


         try(PrintWriter out = ac.getResponse().getWriter()) {
         out.println("<HTML><BODY>");
         out.println("非同期サーブレット・メール配信完了");
         out.println("</BODY></HTML>");
         } catch (IOException ioe) {
         ;
         }
         */
        ac.complete();
        logger.log(Level.INFO, "MailSenderRunnableforEJBServlet Async processing END");
    }

    private void sendMessage() {
        logger.log(Level.INFO, "Current Thread : {0}", Thread.currentThread().toString());

        String email = ac.getRequest().getParameter("email");

        Message msg = new MimeMessage(mailSession);
        try {
            msg = createMessage(email, msg);
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "Send Message Failed :", e);
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
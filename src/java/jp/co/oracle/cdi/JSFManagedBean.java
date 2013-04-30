package jp.co.oracle.cdi;


import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jp.co.oracle.concurrent.managedexec.MyManagedExecutorService;
import jp.co.oracle.ejb.SyncEmailSenderEJB;
import jp.co.oracle.jms.MailAddressRegisterEJB;

@Named(value = "jSFManagedBean")
@RequestScoped
public class JSFManagedBean {
    
    @EJB
    SyncEmailSenderEJB sender;
    
    private String email;
  
    public JSFManagedBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        
    public String pushSyncEmailSenderEJB(){
        sender.sendMessage(getEmail());
        return "syncSendComplete";
    }
    
    public String pushAsyncEmailSenderEJB(){
        sender.asyncSendMessage(getEmail());
        return "asyncSendComplete";
    }
    
    @EJB
    MailAddressRegisterEJB mailRegEJB;
    public String pushJMSRegister(){
        mailRegEJB.registEmailAddress(getEmail());
        return "asyncSendComplete";
    }
    
    
    @EJB
    MyManagedExecutorService myexec;
    public String pushManagedExecService(){
        myexec.execExecutorService();
        return "";
    }
    
}

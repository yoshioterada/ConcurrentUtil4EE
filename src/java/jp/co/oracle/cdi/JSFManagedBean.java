package jp.co.oracle.cdi;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import jp.co.oracle.concurrent.context.ContextServiceManager;
import jp.co.oracle.concurrent.managedexec.MyManagedExecutorService;
import jp.co.oracle.concurrent.scheduled.MyManagedScheduledExecutorService;
import jp.co.oracle.concurrent.tasklisteners.TaskListenerSample;
import jp.co.oracle.concurrent.threadfactory.ThreadFactorySample;
import jp.co.oracle.ejb.SyncAsyncEmailSenderEJB;
import jp.co.oracle.jms.MailRegJMSSendQueueEJB;
import jp.co.oracle.tasks.MyRunnableTask;

@Named(value = "jSFManagedBean")
@RequestScoped
public class JSFManagedBean {

    @EJB
    SyncAsyncEmailSenderEJB sender;
    @EJB
    MailRegJMSSendQueueEJB mailRegJMSEJB;
    @EJB
    MyManagedExecutorService myexec;
    @Inject
    Person person;
    private static final Logger logger = Logger.getLogger(JSFManagedBean.class.getPackage().getName());

    public JSFManagedBean() {
    }

    public String pushSyncEmailSenderEJB() {
        logger.log(Level.INFO, person.toString());
        sender.sendMessage(person.getEntityObject());
        return "syncSendComplete";
    }

    public String pushAsyncEmailSenderEJB() {
        logger.log(Level.INFO, person.toString());
        sender.asyncSendMessage(person.getEntityObject());
        return "asyncSendComplete";
    }

    public String pushJMSRegister() {
        mailRegJMSEJB.registEmailAddress(person.getEntityObject());
        return "asyncSendComplete";
    }

    public String pushManagedExecService() {
        myexec.execExecutorService();
        return "";
    }
    
    public String pushManagedExecService2() {
        myexec.execExecutorService2();
        return "";
    }    
    
    public String pushUnsupportedCreateNewThread(){
        MyRunnableTask task = new MyRunnableTask();
        Thread thread = new Thread(task);
        thread.start();
        return "";
    }

    @EJB
    ContextServiceManager contextManager;
    
    public String pushExecContextService(){
        contextManager.execContextService();
        return "";
    }
    
    @EJB
    MyManagedScheduledExecutorService myScheduleService;
    
    public String pushScheduledExecService(){
        myScheduleService.execScheduledExecutorService();
        return "";
    }

    @EJB
    TaskListenerSample taskListenerSample;
    
    public String pushTaskListenerSample(){
        taskListenerSample.invokeMyTaskListener();
        return "";
    }
    
    @EJB
    ThreadFactorySample threadFacSample;
    
    public String pushThreadFactorySample(){
        threadFacSample.execThreadFactory();
        return "";
    }
}

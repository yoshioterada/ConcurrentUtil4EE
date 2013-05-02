package jp.co.oracle.concurrent.tasklisteners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedExecutors;
import jp.co.oracle.tasks.MyRunnableTask;


@Stateless
public class TaskListenerSample {

    private static final Logger logger = Logger.getLogger(MyRunnableTask.class.getPackage().getName());
    @Resource(name = "concurrent/MyManagedExecutorService")
    ManagedExecutorService manageExecsvc;

    public void invokeMyTaskListener() {
        logger.log(Level.INFO, "Async TaskListener Sample START");
        MyRunnableTask task = new MyRunnableTask();
        MyManagedTaskListener listener = new MyManagedTaskListener();
        
        Runnable taskWithListener = ManagedExecutors.managedTask(task, listener);
        manageExecsvc.execute(task);
        logger.log(Level.INFO, "Async TaskListener Sample END");
    }
}

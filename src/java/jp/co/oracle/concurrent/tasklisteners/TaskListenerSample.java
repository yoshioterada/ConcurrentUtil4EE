package jp.co.oracle.concurrent.tasklisteners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import jp.co.oracle.tasks.MyRunnableTask;
import jp.co.oracle.tasks.MyRunnableWithTaskListner;

@Stateless
public class TaskListenerSample {

    private static final Logger logger = Logger.getLogger(MyRunnableTask.class.getPackage().getName());
    @Resource(name = "concurrent/MyManagedExecutorService")
    ManagedExecutorService manageExecsvc;

    public void invokeMyTaskListener() {
        logger.log(Level.INFO, "Async TaskListener Sample START");
        MyRunnableWithTaskListner task = new MyRunnableWithTaskListner();
        manageExecsvc.execute(task);
        logger.log(Level.INFO, "Async TaskListener Sample END");
    }
}

package jp.co.oracle.concurrent.tasklisteners;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedExecutors;
import jp.co.oracle.tasks.MyCallableTask;
import jp.co.oracle.tasks.MyRunnableTask;


@Stateless
public class TaskListenerSample {

    private static final Logger logger = Logger.getLogger(MyRunnableTask.class.getPackage().getName());
    @Resource(name = "concurrent/MyManagedExecutorService")
    ManagedExecutorService manageExecsvc;

    public void invokeMyTaskListener() {
        logger.log(Level.INFO, "Async RunnableTaskListener START");
        MyRunnableTask task = new MyRunnableTask();
        MyManagedTaskListener listener = new MyManagedTaskListener();
        
        Runnable taskWithListener = ManagedExecutors.managedTask(task, listener);
        manageExecsvc.execute(taskWithListener);
        logger.log(Level.INFO, "Async RunnableTaskListener END");


        logger.log(Level.INFO, "Async RunnableTaskListener START");
        MyCallableTask task2 = new MyCallableTask("Managed Task");
        Callable callTaskWithLiestner = ManagedExecutors.managedTask(task2, listener);
        Future<String> result = manageExecsvc.submit(callTaskWithLiestner);
        try {
            logger.log(Level.INFO, "Async CallableTaskListener Sample END : {0}", result.get().toString());
        } catch (InterruptedException | ExecutionException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}

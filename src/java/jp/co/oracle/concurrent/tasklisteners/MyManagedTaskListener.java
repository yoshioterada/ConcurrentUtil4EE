package jp.co.oracle.concurrent.tasklisteners;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;

public class MyManagedTaskListener implements ManagedTaskListener {

    private static final Logger logger = Logger.getLogger(MyManagedTaskListener.class.getPackage().getName());

    @Override
    public void taskSubmitted(Future<?> future, ManagedExecutorService mes, Object o) {
        logger.log(Level.INFO, "MyManagedTaskListener:Task is Submitted ");
    }

    @Override
    public void taskStarting(Future<?> future, ManagedExecutorService mes, Object o) {
        logger.log(Level.INFO, "MyManagedTaskListener:Task is Starting ");
    }

    @Override
    public void taskAborted(Future<?> future, ManagedExecutorService mes, Object o, Throwable thrwbl) {
        logger.log(Level.INFO, "MyManagedTaskListener:Task is Aborted ");
    }

    @Override
    public void taskDone(Future<?> future, ManagedExecutorService mes, Object o, Throwable thrwbl) {
        logger.log(Level.INFO, "MyManagedTaskListener:Task is Done ");
    }
}

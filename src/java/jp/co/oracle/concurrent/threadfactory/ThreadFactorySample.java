/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.concurrent.threadfactory;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedThreadFactory;
import jp.co.oracle.tasks.MyRunnableTask;

@Stateless
public class ThreadFactorySample {

    private static final Logger logger = Logger.getLogger(ThreadFactorySample.class.getPackage().getName());
    @Resource(name = "concurrent/DefaultManagedThreadFactory")
    ManagedThreadFactory threadFactory;

    public void execThreadFactory() {
        logger.log(Level.INFO, "Thread Factory Sample START");
        MyRunnableTask task = new MyRunnableTask();
        Thread thread = threadFactory.newThread(task);
        thread.start();
        logger.log(Level.INFO, "Thread Factory Sample END");
    }
}

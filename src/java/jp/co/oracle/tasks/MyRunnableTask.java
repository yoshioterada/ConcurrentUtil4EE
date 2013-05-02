/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.tasks;

import java.util.logging.Level;
import java.util.logging.Logger;
import jp.co.oracle.concurrent.managedexec.MyManagedExecutorService;

/**
 *
 * @author tyoshio2002
 */
public class MyRunnableTask implements Runnable {
    private static final Logger logger = Logger.getLogger(MyRunnableTask.class.getPackage().getName());

    @Override
    public void run() {        
        try {
            logger.log(Level.INFO, "ASYNC TASK START");
            Thread.sleep(10000);
            logger.log(Level.INFO, "ASYNC TASK END");
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}

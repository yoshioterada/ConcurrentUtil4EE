package jp.co.oracle.tasks;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author tyoshio2002
 */
public class MyRunnableTask implements Runnable {
    private static final Logger logger = Logger.getLogger(MyRunnableTask.class.getPackage().getName());

    @Override
    public void run() {        
        try {
            Thread current = Thread.currentThread();
            logger.log(Level.INFO, "Current Thread Name: {0}", current.getName());
            URL url = current.getContextClassLoader().getResource("hostname");

            logger.log(Level.INFO, "ASYNC Runnable TASK START");
            Thread.sleep(10000);
            logger.log(Level.INFO, "ASYNC Runnable TASK END");
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}

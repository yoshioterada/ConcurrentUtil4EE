/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.concurrent.ManagedTask;
import static javax.enterprise.concurrent.ManagedTask.IDENTITY_NAME;
import javax.enterprise.concurrent.ManagedTaskListener;
import jp.co.oracle.concurrent.tasklisteners.MyManagedTaskListener;

/**
 *
 * @author tyoshio2002
 */
public class MyRunnableWithTaskListner implements Runnable, ManagedTask {
    private static final Logger logger = Logger.getLogger(MyRunnableWithTaskListner.class.getPackage().getName());
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

    @Override
    public ManagedTaskListener getManagedTaskListener() {
        return new MyManagedTaskListener();
    }

    @Override
    public Map<String, String> getExecutionProperties() {
        HashMap<String, String> prop = new HashMap<>();
        prop.put(IDENTITY_NAME, "ReporterTaskWithManagedTask");
        return prop;
    }
    
}

package jp.co.oracle.tasks;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import org.glassfish.enterprise.concurrent.ContextServiceImpl;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceAdapter;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceImpl;
import org.glassfish.enterprise.concurrent.internal.ManagedThreadPoolExecutor;
import org.glassfish.enterprise.concurrent.spi.ContextSetupProvider;

public class MyCallableTask implements Callable<String> {

    private String name;

    private static final Logger logger = Logger.getLogger(MyCallableTask.class.getPackage().getName());

    public MyCallableTask(String name){
        this.name = name;
    }
    
    @Override
    public String call() throws Exception {
        logger.log(Level.INFO, "ASYNC Callable TASK START");
        StringBuilder returnString = new StringBuilder();
        
        try {
            Thread.sleep(10000);
            returnString.append("Hello World : ");
            returnString.append(name);
            returnString.append("-san");

        } catch ( IllegalArgumentException |SecurityException ex) {
            logger.log(Level.SEVERE, "callable failed ", ex);
        }
        logger.log(Level.INFO, "ASYNC Callable TASK END");
        return new String(returnString);
    }
}

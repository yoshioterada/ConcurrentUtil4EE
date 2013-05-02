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

    private static final Logger logger = Logger.getLogger(MyCallableTask.class.getPackage().getName());
    @Resource(name = "concurrent/MyManagedExecutorService")
    ManagedExecutorService managedExecsvc;

    @Override
    public String call() throws Exception {
        logger.log(Level.INFO, "ASYNC Callable TASK START");
        ManagedExecutorServiceAdapter ma = (ManagedExecutorServiceAdapter) managedExecsvc;
        StringBuilder returnString = new StringBuilder();
        
        try {
            Thread.sleep(10000);
            // リフレクションで ManagedExecutorServiceAdapter の protected フィールド
            // ManagedExecutorServiceImpl executor を取得
            Class maClass = ma.getClass();
            Field field = maClass.getDeclaredField("executor");
            field.setAccessible(true);
            ManagedExecutorServiceImpl mesi = (ManagedExecutorServiceImpl) field.get(ma);
            returnString.append(mesi.getName());
            returnString.append(":\t");

            // ManagedExecutorServiceImpl から ContextServiceImpl,ContextSetupProvider を取得
            ContextServiceImpl contextInfo = (ContextServiceImpl) mesi.getContextService();
            ContextSetupProvider provider = contextInfo.getContextSetupProvider();
            returnString.append(contextInfo.getName());
            returnString.append(":\t");
            
            // ManagedExecutorServiceImpl の protected フィールド
            // ManagedThreadPoolExecutor threadPoolExecutor を取得
            Class myExecServiceImp = mesi.getClass();
            Field threadPoolExecutor = myExecServiceImp.getDeclaredField("threadPoolExecutor");
            threadPoolExecutor.setAccessible(true);
            ManagedThreadPoolExecutor managedThreadPoolExec = (ManagedThreadPoolExecutor) threadPoolExecutor.get(mesi);
            returnString.append(managedThreadPoolExec.toString());

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException ex) {
            logger.log(Level.SEVERE, "callable failed ", ex);
        }
        logger.log(Level.INFO, "ASYNC Callable TASK END");
        return new String(returnString);
    }
}

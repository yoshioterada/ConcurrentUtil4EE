package jp.co.oracle.concurrent.managedexec;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import jp.co.oracle.tasks.MyRunnableTask;
import org.glassfish.enterprise.concurrent.ContextServiceImpl;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceAdapter;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceImpl;
import org.glassfish.enterprise.concurrent.internal.ManagedThreadPoolExecutor;
import org.glassfish.enterprise.concurrent.spi.ContextSetupProvider;

@Stateless
public class MyManagedExecutorService {

    @Resource(name = "concurrent/MyManagedExecutorService")
    ManagedExecutorService managedExecsvc;
    private static final Logger logger = Logger.getLogger(MyManagedExecutorService.class.getPackage().getName());

    
    public void execExecutorService() {
        logger.log(Level.INFO, "METHOD CALL START");
        MyRunnableTask task = new MyRunnableTask();
        managedExecsvc.submit(task);
        logger.log(Level.INFO, "METHOD CALL END");
        debugManagedExecutorService(managedExecsvc);
    }

    private void debugManagedExecutorService(ManagedExecutorService managedExecsvc) {
        ManagedExecutorServiceAdapter ma = (ManagedExecutorServiceAdapter) managedExecsvc;
        try {            
            // リフレクションで ManagedExecutorServiceAdapter の protected フィールド
            // ManagedExecutorServiceImpl executor を取得
            Class maClass = ma.getClass();
            Field field = maClass.getDeclaredField("executor");
            field.setAccessible(true);
            ManagedExecutorServiceImpl mesi = (ManagedExecutorServiceImpl) field.get(ma);
            logger.log(Level.INFO, "ManagedExecutorServiceImpl NAME : {0}", mesi.getName());

            // ManagedExecutorServiceImpl から ContextServiceImpl,ContextSetupProvider を取得
            ContextServiceImpl contextInfo = (ContextServiceImpl) mesi.getContextService();
            ContextSetupProvider provider = contextInfo.getContextSetupProvider();
            logger.log(Level.INFO, "CONTEXT INFO NAME : {0}", contextInfo.getName());

            // ManagedExecutorServiceImpl の protected フィールド
            // ManagedThreadPoolExecutor threadPoolExecutor を取得
            Class myExecServiceImp = mesi.getClass();
            Field threadPoolExecutor = myExecServiceImp.getDeclaredField("threadPoolExecutor");
            threadPoolExecutor.setAccessible(true);
            ManagedThreadPoolExecutor managedThreadPoolExec = (ManagedThreadPoolExecutor) threadPoolExecutor.get(mesi);

            logger.log(Level.INFO, "ManagedThreadPoolExecutor : {0}", managedThreadPoolExec.toString());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException ex) {
            logger.log(Level.SEVERE, "debugManagedExecutorService failed ", ex);
        }
    }
}

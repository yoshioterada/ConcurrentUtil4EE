package jp.co.oracle.concurrent.managedexec;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import jp.co.oracle.tasks.MyCallableTask;
import jp.co.oracle.tasks.MyRunnableTask;
import org.glassfish.enterprise.concurrent.ContextServiceImpl;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceAdapter;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceImpl;
import org.glassfish.enterprise.concurrent.internal.ManagedThreadPoolExecutor;
import org.glassfish.enterprise.concurrent.spi.ContextSetupProvider;

@Stateless
public class MyManagedExecutorService {

    @Resource(name = "concurrent/DefaultManagedExecutorService")
    ManagedExecutorService managedExecsvc;
    private static final Logger logger = Logger.getLogger(MyManagedExecutorService.class.getPackage().getName());

    @Asynchronous
    public void execExecutorService() {
        logger.log(Level.INFO, "METHOD CALL START");
        MyRunnableTask task = new MyRunnableTask();
        managedExecsvc.submit(task);
        logger.log(Level.INFO, "METHOD CALL END");
        debugManagedExecutorService(managedExecsvc);
    }
    
//    @Asynchronous
    public void execExecutorService2(){
        List<Callable<String>> tasks = new ArrayList<>();
        for(int i=0;i<10;i++){
            MyCallableTask task = new MyCallableTask(new Integer(i).toString());            
            tasks.add(task);
        }
        try {
            List<Future<String>> futures = managedExecsvc.invokeAll(tasks);

            for (Future<String> future: futures) {
                try {
                    logger.log(Level.INFO, "Future#get(): {0}", future.get());
                } catch (ExecutionException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
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

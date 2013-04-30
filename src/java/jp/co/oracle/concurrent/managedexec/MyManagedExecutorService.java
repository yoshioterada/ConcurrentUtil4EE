package jp.co.oracle.concurrent.managedexec;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import org.glassfish.enterprise.concurrent.ContextServiceImpl;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceAdapter;
import org.glassfish.enterprise.concurrent.ManagedExecutorServiceImpl;
import org.glassfish.enterprise.concurrent.internal.ManagedThreadPoolExecutor;
import org.glassfish.enterprise.concurrent.spi.ContextSetupProvider;

@Stateless
public class MyManagedExecutorService {

    @Resource(name = "concurrent/MyManagedExecutorService")
    ManagedExecutorService managedExecsvc;

    public void execExecutorService() {
        Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "METHOD CALL START");
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "ASYNC TASK START");
                    Thread.sleep(10000);
                    Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "ASYNC TASK END");
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        managedExecsvc.submit(task);
        Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "METHOD CALL END");
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
            Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "ManagedExecutorServiceImpl NAME : " + mesi.getName());

            // ManagedExecutorServiceImpl から ContextServiceImpl,ContextSetupProvider を取得
            ContextServiceImpl contextInfo = (ContextServiceImpl) mesi.getContextService();
            ContextSetupProvider provider = contextInfo.getContextSetupProvider();
            Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "CONTEXT INFO NAME : " + contextInfo.getName());

            // ManagedExecutorServiceImpl の protected フィールド
            // ManagedThreadPoolExecutor threadPoolExecutor を取得
            Class myExecServiceImp = mesi.getClass();
            Field threadPoolExecutor = myExecServiceImp.getDeclaredField("threadPoolExecutor");
            threadPoolExecutor.setAccessible(true);
            ManagedThreadPoolExecutor managedThreadPoolExec = (ManagedThreadPoolExecutor) threadPoolExecutor.get(mesi);

            Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.INFO, "ManagedThreadPoolExecutor : " + managedThreadPoolExec.toString());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException ex) {
            Logger.getLogger(MyManagedExecutorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

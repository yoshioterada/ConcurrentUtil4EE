package jp.co.oracle.concurrent.threadfactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManageableThread;
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
        ManageableThread mThread = (ManageableThread) thread;
        thread.start();
        while (!mThread.isShutdown()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            logger.log(Level.INFO, "Thread Factory is processing.....");
        }
        logger.log(Level.INFO, "Thread Factory Sample END");
    }

    public void execThreadFactoryWithCustomePool() {
        logger.log(Level.INFO, "Thread Factory Sample START");
        MyRunnableTask task = new MyRunnableTask();
        ExecutorService exec = getThreadPool(threadFactory);
        exec.submit(task);
        logger.log(Level.INFO, "Thread Factory Sample END");
    }

    private static ExecutorService getThreadPool(ManagedThreadFactory threadFac) {
        ExecutorService threadPoolExecutor = null;
        if (threadPoolExecutor == null) {
//            threadPoolExecutor = new ThreadPoolExecutor(
//                    4096, 4096, 5, TimeUnit.SECONDS,
//                    new ArrayBlockingQueue<Runnable>(4096), threadFactory);

            threadPoolExecutor = Executors.newFixedThreadPool(4, threadFac);
//            threadPoolExecutor = new ThreadPoolExecutor(4, 4,
//                                      0L, TimeUnit.MILLISECONDS,
//                                      new LinkedBlockingQueue<Runnable>(),　// Integer.MAX_VALUE の容量
//                                      threadFactory);


//            threadPoolExecutor = Executors.newCachedThreadPool(threadFactory);
//            threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//                                      60L, TimeUnit.SECONDS,
//                                      new SynchronousQueue<Runnable>(),
//                                      threadFactory);

//            threadPoolExecutor = Executors.newSingleThreadExecutor(threadFactory);
//            threadPoolExecutor = new ThreadPoolExecutor(1, 1,
//                                    0L, TimeUnit.MILLISECONDS,
//                                    new LinkedBlockingQueue<Runnable>(),
//                                    threadFactory);

//            threadPoolExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory);
//            threadPoolExecutor = new ScheduledThreadPoolExecutor(1, threadFactory);
        }
        return threadPoolExecutor;
    }
}

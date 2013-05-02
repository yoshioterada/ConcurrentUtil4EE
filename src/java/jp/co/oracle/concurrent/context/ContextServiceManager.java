/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.concurrent.context;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import jp.co.oracle.tasks.MyRunnableTask;


@Stateless
public class ContextServiceManager {

    private ExecutorService threadPoolExecutor = null;

    @Resource(name = "concurrent/DefaultContextService")
    ContextService ctxSvc;
    @Resource(name = "concurrent/DefaultManagedThreadFactory")
    ManagedThreadFactory threadFactory;

    public void execContextService() {
        ExecutorCompletionService<Long> threadPool =
                new ExecutorCompletionService<>(getThreadPool());
        MyRunnableTask task = new MyRunnableTask();
        Runnable runnableTaskWithCtx =
                ctxSvc.createContextualProxy(task, Runnable.class);
        threadPool.submit(runnableTaskWithCtx, Long.MIN_VALUE);
    }

    // 下記のメソッドは本来なら別のシングルトン EJBに実装して
    // アプリケーション・全体で共有するようにした方がよい
    public ExecutorService getThreadPool() {
        
        // Java SE に含まれる java.util.concurrent.ExecutorService を
        // EE コンテナがデフォルトで用意しているスレッド・プールではなく
        // 独自に設定したプールを使用して柔軟に作成する事が可能。
        // Executors.new*****ThreadPool() による ExecutorService 
        // は全て内部的に ThreadPoolExecutor の実装例にすぎず、
        // new ThreadPoolExecutor() でより細かく制御可能。
        if (threadPoolExecutor == null) {
//            threadPoolExecutor = new ThreadPoolExecutor(
//                    4096, 4096, 5, TimeUnit.SECONDS,
//                    new ArrayBlockingQueue<Runnable>(4096), threadFactory);

            threadPoolExecutor = Executors.newFixedThreadPool(4, threadFactory);
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

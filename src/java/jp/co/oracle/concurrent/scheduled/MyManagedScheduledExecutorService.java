package jp.co.oracle.concurrent.scheduled;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import jp.co.oracle.tasks.MyRunnableTask;

@Stateless
public class MyManagedScheduledExecutorService {

    @Resource(name = "concurrent/DefaultManagedScheduledExecutorService")
    ManagedScheduledExecutorService managedScheduledExecsvc;

    public void execScheduledExecutorService() {
        MyRunnableTask task = new MyRunnableTask();
        managedScheduledExecsvc.schedule(task, new MyTrigger(new Date(), 10, 1000));

        // その他、もちろん java.util.concurrent.ScheduledExecutorService
        // で提供されているメソッドも利用可能

        // １分後にタスクを実行する場合
        //managedScheduledExecsvc.schedule(task, 60L, TimeUnit.SECONDS);
    }

    // ManagedScheduledExecutorService に新しく追加された 2 つのメソッド
    // の引数で指定する Trigger の実装例
    // Trigger によっていつ、どのような時にタスクを実行するかを制御可能
    // 各メソッドはコンテキストが付加されていないため、コンテキストを付加する
    // 場合は、ContextService を利用する事で可能
    // ManagedScheduledExecutorService#schedule(Runnable command, Trigger trigger)
    // ManagedScheduledExecutorService#schedule(Callable<V> callable, Trigger trigger)
    class MyTrigger implements Trigger {

        private Date startTime;
        private long delta;
        private long latencyAllowance;

        public MyTrigger(Date startTime,long delta,long latencyAllowance) {
            this.startTime = startTime;
            this.delta = delta;
            this.latencyAllowance = latencyAllowance;
        }
        

        @Override
        public Date getNextRunTime(LastExecution le, Date date){
            if(le != null){
                return new Date(le.getScheduledStart().getTime() + delta);
            } else {
                return startTime;
            }
        }

        @Override
        public boolean skipRun(LastExecution le, Date date) {
            return System.currentTimeMillis() - date.getTime() > latencyAllowance;
        }
    }
}

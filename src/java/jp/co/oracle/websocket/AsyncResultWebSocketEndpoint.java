package jp.co.oracle.websocket;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import jp.co.oracle.tasks.WebSocketAIRSearchTask;
import jp.co.oracle.tasks.WebSocketHotelSearchTask;

@ServerEndpoint(value = "/asyncResult")
public class AsyncResultWebSocketEndpoint {

    private static final Logger logger = Logger.getLogger(AsyncResultWebSocketEndpoint.class.getPackage().getName());
    @Resource(name = "concurrent/DefaultManagedExecutorService")
    ManagedExecutorService managedExecsvc;

    @OnOpen
    public void initOpen(Session session) {
        WebSocketHotelSearchTask task2 = new WebSocketHotelSearchTask(session, 1);
        managedExecsvc.submit(task2);

        task2 = new WebSocketHotelSearchTask(session, 2);
        managedExecsvc.submit(task2);

        task2 = new WebSocketHotelSearchTask(session, 3);
        managedExecsvc.submit(task2);

        WebSocketAIRSearchTask task1 = new WebSocketAIRSearchTask(session, 4);
        managedExecsvc.submit(task1);

        task1 = new WebSocketAIRSearchTask(session, 5);
        managedExecsvc.submit(task1);

        task1 = new WebSocketAIRSearchTask(session, 6);
        managedExecsvc.submit(task1);

        /* 
         * インスタンス生成時に for 文でまわしたら 下記の例外が送出（現在調査中）
         java.lang.IllegalStateException: HeapBuffer has already been disposed
         at org.glassfish.grizzly.memory.HeapBuffer.checkDispose(HeapBuffer.java:802)
         at org.glassfish.grizzly.memory.HeapBuffer.position(HeapBuffer.java:188)
         at org.glassfish.grizzly.nio.transport.TCPNIOAsyncQueueWriter.fillByteBuffer(TCPNIOAsyncQueueWriter.java:194)
         at org.glassfish.grizzly.nio.transport.TCPNIOAsyncQueueWriter.writeComposite0(TCPNIOAsyncQueueWriter.java:151)
         at org.glassfish.grizzly.nio.transport.TCPNIOAsyncQueueWriter.write0(TCPNIOAsyncQueueWriter.java:80)
         at org.glassfish.grizzly.nio.AbstractNIOAsyncQueueWriter.processAsync(AbstractNIOAsyncQueueWriter.java:458)
         at org.glassfish.grizzly.filterchain.DefaultFilterChain.process(DefaultFilterChain.java:110)
         at org.glassfish.grizzly.ProcessorExecutor.execute(ProcessorExecutor.java:77)
         at org.glassfish.grizzly.nio.transport.TCPNIOTransport.fireIOEvent(TCPNIOTransport.java:838)
         at org.glassfish.grizzly.strategies.AbstractIOStrategy.fireIOEvent(AbstractIOStrategy.java:113)
         at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.run0(WorkerThreadIOStrategy.java:115)
         at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.access$100(WorkerThreadIOStrategy.java:55)
         at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy$WorkerThreadRunnable.run(WorkerThreadIOStrategy.java:135)
         at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:564)
         at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.run(AbstractThreadPool.java:544)
         at java.lang.Thread.run(Thread.java:722)
         * 
         * 
         * Grizzly のソースを見るとheap が null になっているようだ。
         ******************************************************************************************
         775 protected final void  [More ...] checkDispose() {
         776   if (heap == null) {
         777     throw new IllegalStateException(
         778              "BufferWrapper has already been disposed",
         779     disposeStackTrace) ;
         780 }
         ******************************************************************************************
         */
    }
}

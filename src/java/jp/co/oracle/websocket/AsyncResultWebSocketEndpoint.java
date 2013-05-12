package jp.co.oracle.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.websocket.OnMessage;
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
        executeTasks(session);
    }

    @OnMessage
    public void receivedMessage(String message, Session session) {
        if (!message.equals("re-execute")) {
            return;
        }
        executeTasks(session);
    }

    private void executeTasks(Session session) {
        ExecutorCompletionService<String> execCompService = new ExecutorCompletionService<>(managedExecsvc);

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 1) {
                WebSocketHotelSearchTask task = new WebSocketHotelSearchTask(i);
                futures.add(execCompService.submit(task));
            }else{
                WebSocketAIRSearchTask task2 = new WebSocketAIRSearchTask(i);
                futures.add(execCompService.submit(task2));
            }
        }
        try {
            for (Future<String> results : futures) {
                String resultString = execCompService.take().get();
                session.getBasicRemote().sendText(resultString);
            }
        } catch (IOException | InterruptedException | ExecutionException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
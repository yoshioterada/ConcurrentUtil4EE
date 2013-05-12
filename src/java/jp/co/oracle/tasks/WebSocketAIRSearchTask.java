package jp.co.oracle.tasks;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

public class WebSocketAIRSearchTask implements Callable<Boolean> {

    private static final Logger logger = Logger.getLogger(WebSocketAIRSearchTask.class.getPackage().getName());
    private Session session;
    private int counter;

    public WebSocketAIRSearchTask(Session session, int counter) {
        this.session = session;
        this.counter = counter;
    }

    @Override
    public Boolean call() {
        boolean result = false;
        try {
            if (counter % 2 == 1) {
                Thread.sleep(3000);
            }
            if (counter % 3 == 1) {
                Thread.sleep(5000);
            }

            session.getBasicRemote().sendText("飛行機検索タスク完了 : 旅行会社" + counter);
            result = true;
        } catch (IOException | InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

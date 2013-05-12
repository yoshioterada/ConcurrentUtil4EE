package jp.co.oracle.tasks;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebSocketAIRSearchTask implements Callable<String> {

    private static final Logger logger = Logger.getLogger(WebSocketAIRSearchTask.class.getPackage().getName());
    private int counter;

    public WebSocketAIRSearchTask(int counter) {
        this.counter = counter;
    }

    @Override
    public String call() {
        String result = "";
        try {
            if (counter % 2 == 1) {
                Thread.sleep(3000);
            }
            if (counter % 3 == 1) {
                Thread.sleep(5000);
            }

            result = "飛行機検索タスク完了 : タスクのランダム ID" + counter;
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

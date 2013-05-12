/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.tasks;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author tyoshio2002
 */
public class WebSocketHotelSearchTask implements Callable<String> {

    private static final Logger logger = Logger.getLogger(WebSocketHotelSearchTask.class.getPackage().getName());
    private int counter;

    public WebSocketHotelSearchTask(int counter) {
        this.counter = counter;
    }

    @Override
    public String call() {
        String result = "";
        try {
            if (counter % 3 == 1) {
                Thread.sleep(2000);
            }
            if (counter % 2 == 1) {
                Thread.sleep(4000);
            }

            result = "ホテル検索タスク完了 : タスクのランダム ID" + counter;;
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }
}

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ping.service;

import ping.service.app.util.ConnectorUtil;
import ping.service.app.util.EndpointUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class App {
    public static void main(String[] args) {
        System.out.println("Begin ping-service initialization...");

        Timer timer = new Timer("TIMER");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (String endpoint : EndpointUtil.endpointList()) {
                    CompletableFuture.runAsync(() -> ConnectorUtil.sendHttpRequest(endpoint));
                }
            }
        };

        long period = 15 * 60 * 1000;       // every 15 minutes
        timer.scheduleAtFixedRate(timerTask, new Date(), period);
        System.out.println("End ping-service initialization...");
    }
}

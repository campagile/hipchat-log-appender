package com.github.campagile.logging;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedLoggingStreamer {

    private TimerConfiguration timerConfiguration;
    public TimedLoggingStreamer(TimerConfiguration timerConfiguration) {
        this.timerConfiguration = timerConfiguration;
    }

    void init(LoggingQueue queue, Outputter outputter) {
        Streamer streamer = new Streamer(queue, outputter);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(streamer, timerConfiguration.getInitialDelay(), timerConfiguration.getPeriod(), TimeUnit.SECONDS);
    }

    private static class Streamer implements Runnable {

        private LoggingQueue queue;
        private Outputter outputter;

        public Streamer(LoggingQueue queue, Outputter outputter) {
            this.queue = queue;
            this.outputter = outputter;
        }

        @Override
        public void run() {
            List<String> latestLogging = queue.getLatestLogging();
            for (String log : latestLogging) {
                try {
                    outputter.write(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

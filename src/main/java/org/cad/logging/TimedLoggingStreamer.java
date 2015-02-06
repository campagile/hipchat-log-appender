package org.cad.logging;

import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedLoggingStreamer {

    void init(LoggingQueue queue, OutputStream outputStream, HipchatOutputter hipchatOutputter) {
        Streamer streamer = new Streamer(queue, outputStream, hipchatOutputter);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(streamer, 5, 2, TimeUnit.SECONDS);
    }

    private static class Streamer implements Runnable {

        private LoggingQueue queue;
        //TODO: Temporary output for development purposes
        private OutputStream outputStream;
        private HipchatOutputter hipchatOutputter;

        public Streamer(LoggingQueue queue, OutputStream outputStream, HipchatOutputter hipchatOutputter) {
            this.queue = queue;
            this.outputStream = outputStream;
            this.hipchatOutputter = hipchatOutputter;
        }

        @Override
        public void run() {
            List<String> latestLogging = queue.getLatestLogging();
            for (String log : latestLogging) {
                try {
                    hipchatOutputter.write(log);
                    outputStream.write(log.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

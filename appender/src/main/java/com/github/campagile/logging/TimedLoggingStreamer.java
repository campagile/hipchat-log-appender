package com.github.campagile.logging;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TODO: RvdC -> Cleanup sysouts.
public class TimedLoggingStreamer {

    private TimerConfiguration timerConfiguration;
    private ScheduledExecutorService service;
    private LatestLoggingContext logContext;
    private Outputter outputter;

    public TimedLoggingStreamer(TimerConfiguration timerConfiguration) {
        this.timerConfiguration = timerConfiguration;
    }

    void init(LatestLoggingContext logContext, Outputter outputter) {
        this.logContext = logContext;
        this.outputter = outputter;
        startTimer();
    }

    void startTimer() {
        Streamer streamer = new Streamer(logContext, this.outputter);
        service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(streamer, timerConfiguration.getInitialDelay(), timerConfiguration.getPeriod(), TimeUnit.SECONDS);
    }

    private static class Streamer implements Runnable {

        private LatestLoggingContext logContext;
        private Outputter outputter;

        public Streamer(LatestLoggingContext logContext, Outputter outputter) {
            this.logContext = logContext;
            this.outputter = outputter;
        }

        @Override
        public void run() {
            String log = String.join("\n", logContext.getLatestLogging());
            try {
                outputter.write(log);
            } catch (Outputter.OutputException e) {
                System.out.println(e.getMessage());
                logContext.addError(e.getMessage(), e);
            }
        }
    }

    void stopTimer() {
        service.shutdown();
    }

    boolean isTimerStopped() {
        return service.isShutdown();
    }
}

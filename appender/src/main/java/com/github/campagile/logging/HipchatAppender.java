package com.github.campagile.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

import java.util.List;

public class HipchatAppender extends AppenderBase<ILoggingEvent> implements LatestLoggingContext {

    private Layout layout;
    private HipchatConfiguration hipchatConfiguration;
    private TimerConfiguration timerConfiguration;
    private LoggingQueue loggingQueue;
    private TimedLoggingStreamer timedLoggingStreamer;
    private static final int IDLE_THRESHOLD = 3;
    private int idleCount = 0;
    private boolean idle = false;

    @Override
    public void start() {
        System.out.println("Starting hipchat appender");
        if(layout == null) {
            addError("No layout set for the appender named ["+ name +"].");
            return;
        }
        if(hipchatConfiguration == null || !hipchatConfiguration.isConfigurationComplete()) {
            addError("No hipchat configuration set for the appender named ["+ name +"].");
            return;
        }
        initializeQueueAndOutputStreamer();
        super.start();
    }

    private void initializeQueueAndOutputStreamer() {
        loggingQueue = new LoggingQueue();
        timedLoggingStreamer =
                new TimedLoggingStreamer(timerConfiguration != null ? timerConfiguration : TimerConfiguration.DEFAULT);
        timedLoggingStreamer.init(this, createOutputter());
    }

    Outputter createOutputter() {
        return new HipchatOutputter(hipchatConfiguration);
    }

    @Override
    protected void append(ILoggingEvent event) {
        idleCount = 0;
        if(idle) {
            startTimer();
        }
        loggingQueue.addToQueue(layout.doLayout(event));
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public void setHipchatConfiguration(HipchatConfiguration hipchatConfiguration) {
        this.hipchatConfiguration = hipchatConfiguration;
    }

    public void setTimerConfiguration(TimerConfiguration timerConfiguration) {
        this.timerConfiguration = timerConfiguration;
    }

    @Override
    public List<String> getLatestLogging() {
        List<String> latestLogging = loggingQueue.getLatestLogging();
        if(latestLogging.isEmpty()) {
            if(++idleCount >= IDLE_THRESHOLD) {
                stopTimer();
                idle = true;
            }
        }
        return latestLogging;
    }

    private synchronized void startTimer() {
        idle = false;
        if(timedLoggingStreamer.isTimerStopped()) {
            timedLoggingStreamer.startTimer();
        }
    }

    private void stopTimer() {
        timedLoggingStreamer.stopTimer();
    }
}

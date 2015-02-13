package com.github.campagile.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

public class HipchatAppender extends AppenderBase<ILoggingEvent> {

    private Layout layout;
    private HipchatConfiguration hipchatConfiguration;
    private TimerConfiguration timerConfiguration;
    private LoggingQueue loggingQueue;
    private TimedLoggingStreamer timedLoggingStreamer;

    @Override
    public void start() {
        if(layout == null) {
            addError("No layout set for the appender named ["+ name +"].");
            return;
        }
        if(hipchatConfiguration == null) {
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
        timedLoggingStreamer.init(loggingQueue, createOutputter());
    }

    Outputter createOutputter() {
        return new HipchatOutputter(hipchatConfiguration);
    }

    @Override
    protected void append(ILoggingEvent event) {
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
}
package org.cad.logging;

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
        if(this.layout == null) {
            addError("No layout set for the appender named ["+ name +"].");
            return;
        }
        loggingQueue = new LoggingQueue();
        timedLoggingStreamer = new TimedLoggingStreamer(timerConfiguration);
        timedLoggingStreamer.init(loggingQueue, createOutputter());
        super.start();
    }

    Outputter createOutputter() {
        return new HipchatOutputter(hipchatConfiguration);
    }

    @Override
    protected void append(ILoggingEvent event) {
        loggingQueue.addToQueue(layout.doLayout(event));
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public HipchatConfiguration getHipchatConfiguration() {
        return hipchatConfiguration;
    }

    public void setHipchatConfiguration(HipchatConfiguration hipchatConfiguration) {
        this.hipchatConfiguration = hipchatConfiguration;
    }

    public TimerConfiguration getTimerConfiguration() {
        return timerConfiguration;
    }

    public void setTimerConfiguration(TimerConfiguration timerConfiguration) {
        this.timerConfiguration = timerConfiguration;
    }
}

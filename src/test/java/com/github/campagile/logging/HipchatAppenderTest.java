package com.github.campagile.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

public class HipchatAppenderTest {

    private static final String LOG_MSG = "log message";
    private LoggerContext loggerContext;

    private Outputter outputter = mock(Outputter.class);
    private HipchatAppender appender = new StubbedAppender();
    private TimerConfiguration timerConfig = createTimerConfiguration();

    @Before
    public void setup() {
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        appender.setTimerConfiguration(timerConfig);
        appender.setHipchatConfiguration(new HipchatConfiguration());
    }

    @Test
    public void startAppenderFailsWhenNoLayoutDefined() throws Exception {
        Logger logger = createLoggerWithLayoutAndAppender(null, appender);

        logger.info(LOG_MSG);
        Thread.sleep(1500);

        verify(outputter, never()).write(LOG_MSG);
    }

    @Test
    public void startAppenderFailsWhenNoHipchatConfigurationDefined() throws Exception {
        appender.setHipchatConfiguration(null);
        Logger logger = createLoggerWithLayoutAndAppender(createLayout(), appender);

        logger.info(LOG_MSG);
        Thread.sleep(1500);

        verify(outputter, never()).write(LOG_MSG);
    }

    @Test
    public void testLogging() throws Exception {
        Logger logger = createLoggerWithLayoutAndAppender(createLayout(), appender);

        logger.info(LOG_MSG);
        Thread.sleep(1500);

        verify(outputter, times(1)).write(LOG_MSG);
    }

    private Logger createLoggerWithLayoutAndAppender(PatternLayout layout, HipchatAppender appender) {
        appender.setLayout(layout);
        appender.setContext(loggerContext);
        appender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(HipchatAppenderTest.class.getName());
        logger.addAppender(appender);
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(false);

        return logger;
    }

    private PatternLayout createLayout() {
        PatternLayout layout = new PatternLayout();
        layout.setPattern("%msg");
        layout.setContext(loggerContext);
        layout.start();
        return layout;
    }

    private class StubbedAppender extends HipchatAppender {

        @Override
        Outputter createOutputter() {
            return outputter;
        }
    }

    private TimerConfiguration createTimerConfiguration() {
        TimerConfiguration timerConfig = new TimerConfiguration();
        timerConfig.setPeriod(1);
        timerConfig.setInitialDelay(0);
        return timerConfig;
    }

}

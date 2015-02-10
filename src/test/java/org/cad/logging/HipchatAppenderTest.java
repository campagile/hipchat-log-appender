package org.cad.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class HipchatAppenderTest {

    private static final String LOG_MSG = "doing some info logging";
    private Outputter outputter = mock(Outputter.class);
    private HipchatAppender appender = new StubbedAppender();
    private PatternLayout patternLayout = new PatternLayout();

    @Before
    public void setup() {
        TimerConfiguration timerConfig = new TimerConfiguration();
        timerConfig.setPeriod(1);
        timerConfig.setInitialDelay(0);
        appender.setTimerConfiguration(timerConfig);
        patternLayout.setPattern("%msg");
    }

    @Test
    public void testLogging() throws IOException, InterruptedException {
        Logger logger = createLogger();

        logger.info(LOG_MSG);
        Thread.sleep(1500);

        verify(outputter, times(1)).write(LOG_MSG);
    }

    private Logger createLogger() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        patternLayout.setContext(lc);
        patternLayout.start();
        appender.setLayout(patternLayout);
        appender.setContext(lc);
        appender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(HipchatAppenderTest.class.getName());
        logger.addAppender(appender);
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(false);

        return logger;
    }

    private class StubbedAppender extends HipchatAppender {
        @Override
        Outputter createOutputter() {
            return outputter;
        }
    }

}

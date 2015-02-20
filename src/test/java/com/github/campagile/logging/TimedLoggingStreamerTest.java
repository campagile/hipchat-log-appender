package com.github.campagile.logging;

import ch.qos.logback.core.spi.ContextAwareBase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TimedLoggingStreamerTest {

    private TimerConfiguration timerConfiguration = new TimerConfiguration();
    private TimedLoggingStreamer timedLoggingStreamer = new TimedLoggingStreamer(timerConfiguration);
    private LoggingQueue queue = mock(LoggingQueue.class);
    private Outputter outputter = mock(Outputter.class);
    private List<String> latestLogging = Arrays.asList("msg1");
    private ContextAwareBase logContext = mock(ContextAwareBase.class);

    @Before
    public void before() {
        timerConfiguration.setInitialDelay(0);
        timerConfiguration.setPeriod(1);
    }

    @Test
    public void timedLogging() throws InterruptedException, IOException {
        when(queue.getLatestLogging()).thenReturn(latestLogging);

        timedLoggingStreamer.init(logContext, queue, outputter);

        Thread.sleep(2500);
        verify(outputter, times(3)).write("msg1");
    }

    @Test
    public void addErrorToLogContextWhenOutputterFails() throws InterruptedException {
        when(queue.getLatestLogging()).thenReturn(latestLogging);
        doThrow(new Outputter.ErrorOpeningConnection("error", new IOException())).when(outputter).write(anyString());

        timedLoggingStreamer.init(logContext, queue, outputter);

        Thread.sleep(500);
        verify(logContext).addError(eq("error"), any(Outputter.ErrorOpeningConnection.class));
    }
}

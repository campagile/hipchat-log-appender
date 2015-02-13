package com.github.campagile.logging;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoggingQueueTest {

    private LoggingQueue loggingQueue;

    @Before
    public void before() {
        loggingQueue = new LoggingQueue();
    }

    @Test
    public void getLatestLogging() {
        loggingQueue.addToQueue("msg1");
        loggingQueue.addToQueue("msg2");
        loggingQueue.addToQueue("msg3");

        List<String> latestLogging = loggingQueue.getLatestLogging();

        assertThat(latestLogging.size(), is(3));
        assertThat(latestLogging.get(0), is("msg1"));
        assertThat(latestLogging.get(1), is("msg2"));
        assertThat(latestLogging.get(2), is("msg3"));
    }

    @Test
    public void getLatestLoggingIsEmptied() {
        loggingQueue.addToQueue("msg1");

        assertThat(loggingQueue.getLatestLogging().size(), is(1));
        assertThat(loggingQueue.getLatestLogging().isEmpty(), is(true));
    }

    @Test
    public void getLatestLoggingDoesNotExceedMaximumSize() {
        String msgA = create1000CharacterString('a');
        String msgB = create1000CharacterString('b');
        String msgC = create1000CharacterString('c');
        String msgD = create1000CharacterString('d');
        String msgE = create1000CharacterString('e');
        String msgF = create1000CharacterString('f');

        loggingQueue.addToQueue(msgA);
        loggingQueue.addToQueue(msgB);
        loggingQueue.addToQueue(msgC);
        loggingQueue.addToQueue(msgD);
        loggingQueue.addToQueue(msgE);
        loggingQueue.addToQueue(msgF);

        List<String> latestLogging = loggingQueue.getLatestLogging();

        assertThat(latestLogging.size(), is(5));

        assertThat(latestLogging.get(0), is(msgA));
        assertThat(latestLogging.get(1), is(msgB));
        assertThat(latestLogging.get(2), is(msgC));
        assertThat(latestLogging.get(3), is(msgD));
        assertThat(latestLogging.get(4), is(msgE));
    }

    private static String create1000CharacterString(char character) {
        StringBuilder sb = new StringBuilder(1000);
        for (int i=0; i<1000; i++) {
            sb.append(character);
        }
        return sb.toString();
    }

    @Test
    public void loggingStopsWhenQueueFull() {
        for (int i=1;i<1010;i++) {
            loggingQueue.addToQueue("m" + i);
        }

        List<String> latestLogging = loggingQueue.getLatestLogging();

        assertThat(latestLogging.size(), is(1000));
        assertThat(latestLogging.get(999), is("m1000"));
    }
}

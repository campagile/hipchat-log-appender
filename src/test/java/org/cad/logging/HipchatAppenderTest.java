package org.cad.logging;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HipchatAppenderTest {

    private static final Logger LOG = LoggerFactory.getLogger(HipchatAppender.class);

    @Test
    public void testLogging() throws InterruptedException {
        for(int i=0;i<20;i++) {
            LOG.info("doing some info logging " + i);
            Thread.sleep(1000);
        }
    }

}

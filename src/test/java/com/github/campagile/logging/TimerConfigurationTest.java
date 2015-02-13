package com.github.campagile.logging;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimerConfigurationTest {

    private TimerConfiguration config = new TimerConfiguration();

    @Test
    public void configuration() {
        config.setInitialDelay(20);
        config.setPeriod(10);

        assertThat(config.getInitialDelay(), is(20));
        assertThat(config.getPeriod(), is(10));
    }
}

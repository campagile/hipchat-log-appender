package com.github.campagile.logging;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class HipchatConfigurationTest {

    private HipchatConfiguration config = new HipchatConfiguration();

    @Test
    public void configuration() {
        config.setColor("color");
        config.setEndpoint("endpoint");
        config.setFrom("from");
        config.setRoom("room");
        config.setToken("token");
        config.setUrl("url");

        assertThat(config.getColor(), is("color"));
        assertThat(config.getEndpoint(), is("endpoint"));
        assertThat(config.getFrom(), is("from"));
        assertThat(config.getRoom(), is("room"));
        assertThat(config.getToken(), is("token"));
        assertThat(config.getUrl(), is("url"));
    }
}

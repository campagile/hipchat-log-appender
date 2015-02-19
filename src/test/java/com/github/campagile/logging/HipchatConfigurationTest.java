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

    @Test
    public void configurationComplete() {
        assertThat(createConfig("color", "endpoint", "from", "room", "token", "url").isConfigurationComplete(), is(true));
    }

    @Test
    public void configurationInComplete() {
        assertThat(createConfig("color", "endpoint", "from", "room", "token", null).isConfigurationComplete(), is(false));
        assertThat(createConfig("color", "endpoint", "from", "room", null, "url").isConfigurationComplete(), is(false));
        assertThat(createConfig("color", "endpoint", "from", null, "token", "url").isConfigurationComplete(), is(false));
        assertThat(createConfig("color", "endpoint", null, "room", "token", "url").isConfigurationComplete(), is(false));
        assertThat(createConfig("color", null, "from", "room", "token", "url").isConfigurationComplete(), is(false));
        assertThat(createConfig(null, "endpoint", "from", "room", "token", "url").isConfigurationComplete(), is(false));
    }

    private HipchatConfiguration createConfig(String color, String endpoint, String from, String room, String token, String url) {
        HipchatConfiguration config = new HipchatConfiguration();
        config.setColor(color);
        config.setEndpoint(endpoint);
        config.setFrom(from);
        config.setRoom(room);
        config.setToken(token);
        config.setUrl(url);
        return config;
    }
}

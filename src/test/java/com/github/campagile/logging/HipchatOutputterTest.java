package com.github.campagile.logging;

import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HipchatOutputterTest {
    private HttpsURLConnection connection = mock(HttpsURLConnection.class);
    private OutputStream outputStream = mock(OutputStream.class);
    private InputStream inputStream = mock(InputStream.class);
    private HipchatConfiguration hipchatConfiguration = createHipchatConfiguration();
    private HipchatOutputter outputter = new StubbedHipchatOutputter(hipchatConfiguration);

    @Before
    public void before() throws IOException {
        when(connection.getOutputStream()).thenReturn(outputStream);
        when(connection.getInputStream()).thenReturn(inputStream);
        when(inputStream.read(any(byte[].class))).thenReturn(1);
    }

    @Test
    public void sendOutput() throws IOException {
        //outputter.write("msg1");
        //Testing github jenkins plugin

    }

    private class StubbedHipchatOutputter extends HipchatOutputter {

        public StubbedHipchatOutputter(HipchatConfiguration config) {
            super(config);
        }

        @Override
        HttpsURLConnection openConnection(URL obj) throws IOException {
            return connection;
        }
    }

    private HipchatConfiguration createHipchatConfiguration() {
        HipchatConfiguration config = new HipchatConfiguration();
        config.setColor("color");
        config.setFrom("from");
        config.setRoom("room");
        config.setToken("token");
        config.setUrl("https://github.com/campagile");
        config.setEndpoint("hipchat-log-appender");
        return config;
    }
}

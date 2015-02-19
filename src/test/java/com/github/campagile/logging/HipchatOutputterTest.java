package com.github.campagile.logging;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.mock;

public class HipchatOutputterTest {
    private HttpsURLConnection connection = mock(HttpsURLConnection.class);


    private class StubbedHipchatOutputter extends HipchatOutputter {

        public StubbedHipchatOutputter(HipchatConfiguration config) {
            super(config);
        }

        @Override
        HttpsURLConnection openConnection(URL obj) throws IOException {
            return super.openConnection(obj);
        }
    }

    private HipchatConfiguration createHipchatConfiguration() {
        HipchatConfiguration config = new HipchatConfiguration();
        config.setColor("color");
        config.setEndpoint("endpoint");
        config.setFrom("from");
        config.setRoom("room");
        config.setToken("token");
        config.setUrl("url");
        return config;
    }
}

package com.github.campagile.logging;

import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;

import static org.mockito.Mockito.*;

public class HipchatOutputterTest {
    private static final String MSG_1 = "msg1";
    private static final int OK_RESPONSE = 200;
    private static final int NOK_RESPONSE = 400;
    private HttpsURLConnection connection = mock(HttpsURLConnection.class);
    private OutputStream outputStream = mock(OutputStream.class);
    private InputStream inputStream = new ByteArrayInputStream("msg1".getBytes());
    private HipchatConfiguration hipchatConfiguration = createHipchatConfiguration();
    private HipchatOutputter outputter = new StubbedHipchatOutputter(hipchatConfiguration);

    @Before
    public void before() throws IOException {
        when(connection.getOutputStream()).thenReturn(outputStream);
        when(connection.getInputStream()).thenReturn(inputStream);
    }

    @Test
    public void hipchatPostMessage() throws IOException {
        when(connection.getResponseCode()).thenReturn(OK_RESPONSE);
        String expectedOut =
                        "room_id=room" +
                        "&format=json" +
                        "&auth_token=token" +
                        "&from=from" +
                        "&color=color" +
                        "&message_format=text" +
                        "&message="+MSG_1;

        outputter.write(MSG_1);

        for (int i = 0 ; i < expectedOut.length(); i++) {
            verify(outputStream, atLeastOnce()).write((byte) expectedOut.charAt(i));
        }
    }

    @Test(expected = Outputter.UnexpectedResponse.class)
    public void failureOnIncorrectResponseCode() throws IOException {
        when(connection.getResponseCode()).thenReturn(NOK_RESPONSE);

        outputter.write(MSG_1);
    }

    @Test(expected = Outputter.IncorrectUrl.class)
    public void failureOnIncorrectUrl() throws IOException {
        hipchatConfiguration.setUrl("incorrect");

        outputter.write(MSG_1);
    }

    @Test(expected = Outputter.ErrorOpeningConnection.class)
    public void failureWhenOpeningConnection() throws IOException {
        doThrow(ProtocolException.class).when(connection).setRequestMethod("POST");

        outputter.write(MSG_1);
    }

    @Test(expected = Outputter.ErrorInHipchatCommunication.class)
    public void failureInHipchatCommunication() throws IOException {
        doThrow(IOException.class).when(connection).getOutputStream();

        outputter.write(MSG_1);
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

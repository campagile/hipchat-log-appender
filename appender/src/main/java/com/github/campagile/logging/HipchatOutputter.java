package com.github.campagile.logging;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HipchatOutputter implements Outputter {

    private HipchatConfiguration config;

    public HipchatOutputter(HipchatConfiguration config) {
        this.config = config;
    }

    @Override
    public void write(String output) {
        URL url = null;
        try {
            url = new URL(config.getUrl() + "/" + config.getEndpoint());
        } catch (MalformedURLException e) {
            throw new IncorrectUrl(config.getUrl() + "/" + config.getEndpoint());
        }
        HttpsURLConnection con = null;
        try {
            con = openConnection(url);
            con.setRequestMethod("POST");
        } catch (IOException e) {
            throw new ErrorOpeningConnection(config.getUrl() + "/" + config.getEndpoint(), e);
        }

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", "" + Integer.toString(output.getBytes().length));
        con.setRequestProperty("Content-Language", "en-US");
        con.setDoInput(true);
        con.setDoOutput(true);

        try {
            DataOutputStream outputStream = new DataOutputStream(con.getOutputStream());
            outputStream.writeBytes(createRequestBody(output));
            outputStream.flush();
            outputStream.close();

            int responseCode = con.getResponseCode();
            if(responseCode != 200) {
                throw new UnexpectedResponse(responseCode);
            }
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            String response = "";

            while ((inputLine = in.readLine()) != null) {
                response += inputLine;
            }
            in.close();
        } catch (IOException e) {
            throw new ErrorInHipchatCommunication(e);
        }
    }

    HttpsURLConnection openConnection(URL obj) throws IOException {
        return (HttpsURLConnection) obj.openConnection();
    }

    private String createRequestBody(String output) {
        String bodyContent = "room_id=" + config.getRoom();
        bodyContent += "&format=json";
        bodyContent += "&auth_token=" + config.getToken();
        bodyContent += "&from=" + config.getFrom();
        bodyContent += "&color=" + config.getColor();
        bodyContent += "&message_format=text";
        bodyContent += "&message="+ encode(output);
        return bodyContent;
    }

    private String encode(String logData) {
        try {
            return URLEncoder.encode(logData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.github.campagile.logging;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class HipchatOutputter implements Outputter {

    private HipchatConfiguration config;

    public HipchatOutputter(HipchatConfiguration config) {
        this.config = config;
    }

    @Override
    public void write(String output) throws IOException {
        URL obj = new URL(config.getUrl() + "/" + config.getEndpoint());
        HttpsURLConnection con = openConnection(obj);

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", "" + Integer.toString(output.getBytes().length));
        con.setRequestProperty("Content-Language", "en-US");
        con.setDoInput(true);
        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(createRequestBody(output));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        String response = "";

        while ((inputLine = in.readLine()) != null) {
            response += inputLine;
        }
        in.close();

        System.out.println(response);

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

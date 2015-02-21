package com.github.campagile.logging;

public class HipchatConfiguration {
    private String url;
    private String endpoint;
    private String token;
    private String room;
    private String color;
    private String from;

    public HipchatConfiguration() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    boolean isConfigurationComplete() {
        boolean complete = true;

        complete = complete && (url != null && !url.isEmpty());
        complete = complete && (endpoint != null && !endpoint.isEmpty());
        complete = complete && (room != null && !room.isEmpty());
        complete = complete && (color != null && !color.isEmpty());
        complete = complete && (from != null && !from.isEmpty());
        complete = complete && (token != null && !token.isEmpty());

        return complete;
    }
}

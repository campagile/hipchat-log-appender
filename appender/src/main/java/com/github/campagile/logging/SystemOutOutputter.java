package com.github.campagile.logging;

//For testing purposes
public class SystemOutOutputter implements Outputter {

    @Override
    public void write(String output) {
        System.out.println(output);
    }
}

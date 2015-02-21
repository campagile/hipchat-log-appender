package com.github.campagile.tryout;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Working on:
 * Blocking: Internally a Timer service is used. This has to be shutdown when the application shuts down. Currently the jvm keeps running.
 */
public class TryoutStuff {
    private static final Logger LOG = LoggerFactory.getLogger(TryoutStuff.class);

    public static void main(String[] args) {
        System.out.println("In main");
        LOG.error("Start tracking errors in Hipchat!");
        System.out.println("And again");
        stopLogging();
    }

    private static void stopLogging() {
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory instanceof LoggerContext) {
            System.out.println("stopping loggercontext");
            LoggerContext context = (LoggerContext) loggerFactory;
            context.stop();
        }
    }
}

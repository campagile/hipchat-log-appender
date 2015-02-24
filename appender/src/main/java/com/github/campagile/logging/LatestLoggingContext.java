package com.github.campagile.logging;

import ch.qos.logback.core.spi.ContextAware;

import java.util.List;

public interface LatestLoggingContext extends ContextAware {
    List<String> getLatestLogging();
}

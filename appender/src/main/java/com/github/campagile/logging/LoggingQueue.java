package com.github.campagile.logging;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LoggingQueue {
    private static final int MAX_QUEUE_SIZE = 1000;
    private static final int MAX_LOGGING_SIZE = 5000;

    private Deque<String> queue = new ArrayDeque<String>();

    void addToQueue(String log) {
        if(queue.size() < MAX_QUEUE_SIZE) {
            queue.addLast(log);
        }
    }

    List<String> getLatestLogging() {
        List<String> latest = new ArrayList<String>();
        int totalSize = 0;
        while(queue.peek() != null) {
            totalSize += queue.peek().length();
            if(totalSize > MAX_LOGGING_SIZE) {
                break;
            }
            latest.add(queue.pop());
        }
        return latest;
    }
}

package com.github.campagile.logging;

import java.io.IOException;

public interface Outputter {

    void write(String output);

    static class OutputException extends RuntimeException {
        public OutputException(String message) {
            super(message);
        }
        public OutputException(String message, Exception cause) {
            super(message, cause);
        }
        public OutputException(Exception cause) {
            super(cause);
        }
    }

    static class IncorrectUrl extends OutputException {
        public IncorrectUrl(String message) {
            super(message);
        }
    }

    static class ErrorOpeningConnection extends OutputException {
        public ErrorOpeningConnection(String message, IOException cause) {
            super(message, cause);
        }
    }

    static class ErrorInHipchatCommunication extends OutputException {
        public ErrorInHipchatCommunication(IOException e) {
            super(e);
        }
    }

    static class UnexpectedResponse extends OutputException {
        public UnexpectedResponse(int responseCode) {
            super(Integer.toString(responseCode));
        }
    }
}

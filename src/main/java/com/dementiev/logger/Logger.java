package com.dementiev.logger;

public class Logger {

    private final String loggingClass;

    public Logger(String loggingClass) {
        this.loggingClass = loggingClass;
    }

    public void info(String message) {
        System.out.println(loggingClass + " INFO: " + message);
    }

    public void warning(String message, Throwable error) {
        System.out.println(loggingClass + " WARNING: " + message);
        System.out.println(error.getLocalizedMessage() + '\n');
    }

    public void error(String message, Throwable error) {
        System.out.println(loggingClass + " ERROR: " + message);
        error.printStackTrace(System.out);
    }
}

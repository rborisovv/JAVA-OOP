package com.solid.exercise.services;

import java.io.IOException;

public interface Logger {
    void logInfo(String info) throws IOException;

    void logWarning(String warning) throws IOException;

    void logError(String error) throws IOException;

    void logCritical(String critical) throws IOException;

    void logFatal(String fatal) throws IOException;
}

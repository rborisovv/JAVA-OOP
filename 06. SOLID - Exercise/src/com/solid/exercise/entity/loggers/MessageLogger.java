package com.solid.exercise.entity.loggers;

import com.solid.exercise.entity.enums.ReportLevel;
import com.solid.exercise.services.Appender;
import com.solid.exercise.services.Logger;

import java.io.IOException;

public class MessageLogger implements Logger {
    private final Appender appender;

    public MessageLogger(Appender appender) {
        this.appender = appender;
    }

    @Override
    public void logInfo(String message) {
        logToConsole(ReportLevel.INFO, message);
    }

    @Override
    public void logWarning(String message) {
        logToConsole(ReportLevel.WARNING, message);
    }

    @Override
    public void logError(String message) {
        logToConsole(ReportLevel.ERROR, message);
    }

    @Override
    public void logCritical(String message) {
        logToConsole(ReportLevel.CRITICAL, message);
    }

    @Override
    public void logFatal(String message) {
        logToConsole(ReportLevel.FATAL, message);
    }

    public void logToConsole(ReportLevel reportLevel, String message) {
        try {
            String formattedMessage = appender.append(reportLevel, message);
            System.out.println(formattedMessage);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
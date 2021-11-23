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
    public void logInfo(String message) throws IOException {
        System.out.println(appender.append(ReportLevel.INFO, message));
    }

    @Override
    public void logWarning(String message) throws IOException {
        System.out.println(appender.append(ReportLevel.WARNING, message));
    }

    @Override
    public void logError(String message) throws IOException {
        System.out.println(appender.append(ReportLevel.ERROR, message));
    }

    @Override
    public void logCritical(String message) throws IOException {
        System.out.println(appender.append(ReportLevel.CRITICAL, message));
    }

    @Override
    public void logFatal(String message) throws IOException {
        System.out.println(appender.append(ReportLevel.FATAL, message));
    }
}
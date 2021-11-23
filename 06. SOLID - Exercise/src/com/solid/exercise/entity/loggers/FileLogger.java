package com.solid.exercise.entity.loggers;

import com.solid.exercise.entity.enums.ReportLevel;
import com.solid.exercise.services.Appender;
import com.solid.exercise.services.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {
    private final Appender appender;
    private final BufferedWriter writer;

    public FileLogger(Appender appender) throws IOException {
        this.appender = appender;
        this.writer = new BufferedWriter(new FileWriter("src/com/solid/exercise/logs/document.txt"));
    }

    @Override
    public void logInfo(String message) {
        logToFile(ReportLevel.INFO, message);
    }

    @Override
    public void logWarning(String message) {
        logToFile(ReportLevel.WARNING, message);
    }

    @Override
    public void logError(String message) {
        logToFile(ReportLevel.ERROR, message);
    }

    @Override
    public void logCritical(String message) {
        logToFile(ReportLevel.CRITICAL, message);
    }

    @Override
    public void logFatal(String message) {
        logToFile(ReportLevel.FATAL, message);
    }

    private void logToFile(ReportLevel reportLevel, String message) {
        try {
            String formattedMsg = appender.append(reportLevel, message);
            writer.write(formattedMsg);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
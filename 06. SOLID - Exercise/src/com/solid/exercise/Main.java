package com.solid.exercise;

import com.solid.exercise.entity.appenders.ConsoleAppender;
import com.solid.exercise.entity.appenders.FileAppender;
import com.solid.exercise.entity.layouts.SimpleLayout;
import com.solid.exercise.entity.loggers.FileLogger;
import com.solid.exercise.entity.loggers.MessageLogger;
import com.solid.exercise.services.Appender;
import com.solid.exercise.services.Layout;
import com.solid.exercise.services.Logger;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Layout layout = new SimpleLayout();
        Appender appender = new FileAppender(layout);
        Logger logger = new FileLogger(appender);
        String criticalMsg = "Server is down!";
        logger.logCritical(criticalMsg);
        BufferedReader reader =
                new BufferedReader(new FileReader("src/com/solid/exercise/logs/document.txt"));
        String msg = reader.readLine();
        System.out.println(msg);

        Appender consoleApp = new ConsoleAppender(layout);
        Logger consoleLogger = new MessageLogger(consoleApp);
        String errorMsg = "Can't find d3xc.dll!";
        consoleLogger.logError(errorMsg);
    }
}
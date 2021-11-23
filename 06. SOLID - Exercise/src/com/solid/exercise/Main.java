package com.solid.exercise;

import com.solid.exercise.entity.appenders.FileAppender;
import com.solid.exercise.entity.layouts.XmlLayout;
import com.solid.exercise.entity.loggers.FileLogger;
import com.solid.exercise.services.Appender;
import com.solid.exercise.services.Layout;
import com.solid.exercise.services.Logger;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Layout xmlLayout = new XmlLayout();
        Appender xmlFileAppender = new FileAppender(xmlLayout);
        Logger xmlFileLogger = new FileLogger(xmlFileAppender);
        String fatalMsg = "Server is down!";
        xmlFileLogger.logFatal(fatalMsg);
        BufferedReader reader =
                new BufferedReader(new FileReader("src/com/solid/exercise/logs/document.txt"));
        String xmlMsg = reader.readLine();
        while (xmlMsg != null) {
            System.out.println(xmlMsg);
            xmlMsg = reader.readLine();
        }
    }
}
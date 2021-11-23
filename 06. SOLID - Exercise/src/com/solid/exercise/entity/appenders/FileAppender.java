package com.solid.exercise.entity.appenders;

import com.solid.exercise.entity.enums.ReportLevel;
import com.solid.exercise.services.Appender;
import com.solid.exercise.services.Layout;

public class FileAppender implements Appender {
    private final Layout layout;

    public FileAppender(Layout layout) {
        this.layout = layout;
    }

    @Override
    public String append(ReportLevel reportLevel, String text) {
        return layout.format(reportLevel, text).trim();
    }
}
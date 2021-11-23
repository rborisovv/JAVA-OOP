package com.solid.exercise.entity.layouts;

import com.solid.exercise.entity.enums.ReportLevel;
import com.solid.exercise.services.Layout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLayout implements Layout {

    @Override
    public String format(ReportLevel reportLevel, String text) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //TODO: Find a way to display PM/AM accordingly
        return String.format("%s PM - %s - %s%n", currentDateTime.format(formatter), reportLevel, text);
    }
}
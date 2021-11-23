package com.solid.exercise.entity.layouts;

import com.solid.exercise.entity.enums.ReportLevel;
import com.solid.exercise.services.Layout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XmlLayout implements Layout {
    public XmlLayout() {

    }


    @Override
    public String format(ReportLevel reportLevel, String text) {
        StringBuilder result = new StringBuilder();
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
        result.append("<log>").append(System.lineSeparator())
                .append(String.format("<date>%s PM</date>", dateTime)).append(System.lineSeparator())
                .append(String.format("<level>%s</level>", reportLevel)).append(System.lineSeparator())
                .append(String.format("<message>%s</message>", text)).append(System.lineSeparator())
                .append("</log>");
        return result.toString().trim();
    }
}

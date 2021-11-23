package com.solid.exercise.services;

import com.solid.exercise.entity.enums.ReportLevel;

import java.io.IOException;

public interface Appender {
    String append(ReportLevel reportLevel, String text) throws IOException;
}
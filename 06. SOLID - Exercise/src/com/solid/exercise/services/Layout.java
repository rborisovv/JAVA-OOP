package com.solid.exercise.services;

import com.solid.exercise.entity.enums.ReportLevel;

public interface Layout {
    String format(ReportLevel reportLevel, String text);
}
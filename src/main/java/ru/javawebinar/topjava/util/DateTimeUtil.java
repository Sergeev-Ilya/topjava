package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime MIN = LocalDateTime.of(1,0,0,0,0);
    private static final LocalDateTime MAX = LocalDateTime.of(3000,0,0,0,0);

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate startDate) {
        return startDate != null ? startDate.atStartOfDay() : MIN;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate endDate) {
        return endDate != null ? endDate.plusDays(1).atStartOfDay() : MAX;
    }
}


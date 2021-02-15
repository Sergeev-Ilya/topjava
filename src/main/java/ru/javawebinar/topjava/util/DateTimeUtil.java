package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime MIN = LocalDateTime.of(1,1,1,0,0);
    private static final LocalDateTime MAX = LocalDateTime.of(3000,1,1,0,0);

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0) &&
                (endTime == null || lt.compareTo(endTime) < 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime atStartOfDayOrMin(@Nullable LocalDate startDate) {
        return startDate != null ? startDate.atStartOfDay() : MIN;
    }

    public static LocalDateTime atStartOfNextDayOrMax(@Nullable LocalDate endDate) {
        return endDate != null ? endDate.plusDays(1).atStartOfDay() : MAX;
    }

    public static LocalDate parseLocalDate(@Nullable String localDate) {
        return StringUtils.isEmpty(localDate) ? null : LocalDate.parse(localDate);
    }

    public static LocalTime parseLocalTime(@Nullable String localTime) {
        return StringUtils.isEmpty(localTime) ? null : LocalTime.parse(localTime);
    }
}


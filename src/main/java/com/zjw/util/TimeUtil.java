package com.zjw.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Date and time utility methods.
 */
public final class TimeUtil {

  public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");
  public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

  private TimeUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static LocalDateTime now() {
    return LocalDateTime.now(DEFAULT_ZONE_ID);
  }

  public static LocalDateTime now(ZoneId zoneId) {
    return LocalDateTime.now(zoneId == null ? DEFAULT_ZONE_ID : zoneId);
  }

  public static long nowMillis() {
    return Instant.now().toEpochMilli();
  }

  public static String format(LocalDateTime dateTime) {
    return format(dateTime, DEFAULT_DATE_TIME_PATTERN);
  }

  public static String format(LocalDateTime dateTime, String pattern) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static LocalDateTime parseLocalDateTime(String value) {
    return parseLocalDateTime(value, DEFAULT_DATE_TIME_PATTERN);
  }

  public static LocalDateTime parseLocalDateTime(String value, String pattern) {
    if (StringUtil.isBlank(value)) {
      return null;
    }
    try {
      return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
    } catch (DateTimeParseException e) {
      throw new IllegalStateException("Failed to parse datetime: " + value, e);
    }
  }

  public static LocalDate parseLocalDate(String value) {
    if (StringUtil.isBlank(value)) {
      return null;
    }
    try {
      return LocalDate.parse(value, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    } catch (DateTimeParseException e) {
      throw new IllegalStateException("Failed to parse date: " + value, e);
    }
  }

  public static LocalDateTime startOfDay(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.atStartOfDay();
  }

  public static LocalDateTime endOfDay(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.atTime(LocalTime.MAX);
  }

  public static LocalDateTime addDays(LocalDateTime dateTime, long days) {
    return dateTime == null ? null : dateTime.plusDays(days);
  }

  public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
    return dateTime == null ? null : dateTime.plusHours(hours);
  }

  public static long betweenSeconds(LocalDateTime start, LocalDateTime end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException("start and end must not be null");
    }
    return end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC);
  }

  public static long toEpochMilli(LocalDateTime dateTime) {
    if (dateTime == null) {
      throw new IllegalArgumentException("dateTime must not be null");
    }
    return dateTime.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
  }

  public static LocalDateTime fromEpochMilli(long epochMilli) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), DEFAULT_ZONE_ID);
  }
}

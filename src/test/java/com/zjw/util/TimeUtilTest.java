package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeUtilTest {

  @Test
  @DisplayName("Format and parse datetime")
  void shouldFormatAndParseDateTime() {
    LocalDateTime dateTime = LocalDateTime.of(2026, 5, 16, 12, 34, 56);

    String formatted = TimeUtil.format(dateTime);
    assertEquals("2026-05-16 12:34:56", formatted);
    assertEquals(dateTime, TimeUtil.parseLocalDateTime(formatted));
  }

  @Test
  @DisplayName("Date helpers")
  void shouldHandleDateHelpers() {
    LocalDate date = LocalDate.of(2026, 5, 16);

    assertEquals(LocalDateTime.of(2026, 5, 16, 0, 0), TimeUtil.startOfDay(date));
    assertNotNull(TimeUtil.endOfDay(date));
    assertEquals(LocalDateTime.of(2026, 5, 18, 12, 34, 56), TimeUtil.addDays(
        LocalDateTime.of(2026, 5, 16, 12, 34, 56), 2));
    assertEquals(LocalDateTime.of(2026, 5, 16, 14, 34, 56), TimeUtil.addHours(
        LocalDateTime.of(2026, 5, 16, 12, 34, 56), 2));
  }

  @Test
  @DisplayName("Epoch conversion")
  void shouldConvertEpoch() {
    LocalDateTime dateTime = LocalDateTime.of(2026, 5, 16, 12, 34, 56);
    long epochMilli = TimeUtil.toEpochMilli(dateTime);

    assertEquals(dateTime, TimeUtil.fromEpochMilli(epochMilli));
  }

  @Test
  @DisplayName("Reject null in betweenSeconds")
  void shouldRejectNullForBetweenSeconds() {
    assertThrows(IllegalArgumentException.class, () -> TimeUtil.betweenSeconds(null, LocalDateTime.now()));
  }
}

package com.zjw.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * String utility methods.
 */
public final class StringUtil {

  private StringUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  public static boolean isNotBlank(String value) {
    return !isBlank(value);
  }

  public static String defaultString(String value) {
    return value == null ? "" : value;
  }

  public static String defaultIfBlank(String value, String defaultValue) {
    return isBlank(value) ? defaultValue : value;
  }

  public static String trimToNull(String value) {
    if (value == null) {
      return null;
    }
    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }

  public static String trimToEmpty(String value) {
    return value == null ? "" : value.trim();
  }

  public static boolean equalsIgnoreCase(String left, String right) {
    return Objects.equals(left, right) || (left != null && right != null && left.equalsIgnoreCase(right));
  }

  public static String join(Collection<?> values, String delimiter) {
    if (values == null || values.isEmpty()) {
      return "";
    }
    StringJoiner joiner = new StringJoiner(delimiter == null ? "" : delimiter);
    for (Object value : values) {
      joiner.add(value == null ? "" : String.valueOf(value));
    }
    return joiner.toString();
  }

  public static List<String> splitAndTrim(String value, String delimiterRegex) {
    List<String> result = new ArrayList<>();
    if (isBlank(value)) {
      return result;
    }
    String[] parts = value.split(delimiterRegex);
    for (String part : parts) {
      String trimmed = trimToNull(part);
      if (trimmed != null) {
        result.add(trimmed);
      }
    }
    return result;
  }

  public static String mask(String value, int prefixVisible, int suffixVisible) {
    if (value == null) {
      return null;
    }
    if (prefixVisible < 0 || suffixVisible < 0) {
      throw new IllegalArgumentException("prefixVisible and suffixVisible must be >= 0");
    }
    if (prefixVisible + suffixVisible >= value.length()) {
      return value;
    }
    StringBuilder builder = new StringBuilder(value.length());
    builder.append(value, 0, prefixVisible);
    builder.append("*".repeat(value.length() - prefixVisible - suffixVisible));
    builder.append(value, value.length() - suffixVisible, value.length());
    return builder.toString();
  }
}

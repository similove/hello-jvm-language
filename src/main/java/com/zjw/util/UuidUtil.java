package com.zjw.util;

import java.util.UUID;

/**
 * UUID utility methods.
 */
public final class UuidUtil {

  private UuidUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static String randomUuid() {
    return UUID.randomUUID().toString();
  }

  public static String randomUuid(boolean withHyphen) {
    String value = randomUuid();
    return withHyphen ? value : value.replace("-", "");
  }

  public static String compactUuid() {
    return randomUuid(false);
  }
}

package com.zjw.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5 utility methods.
 */
public final class Md5Util {

  private Md5Util() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static String md5(String value) {
    if (value == null) {
      return null;
    }
    return md5(value.getBytes(StandardCharsets.UTF_8));
  }

  public static String md5(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      byte[] hash = digest.digest(bytes);
      StringBuilder builder = new StringBuilder(hash.length * 2);
      for (byte b : hash) {
        builder.append(String.format("%02x", b));
      }
      return builder.toString();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to calculate MD5", e);
    }
  }
}

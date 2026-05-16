package com.zjw.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64 utility methods.
 */
public final class Base64Util {

  private Base64Util() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static String encode(String value) {
    if (value == null) {
      return null;
    }
    return encode(value.getBytes(StandardCharsets.UTF_8));
  }

  public static String encode(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static String decodeToString(String value) {
    byte[] decoded = decode(value);
    return decoded == null ? null : new String(decoded, StandardCharsets.UTF_8);
  }

  public static byte[] decode(String value) {
    if (value == null) {
      return null;
    }
    return Base64.getDecoder().decode(value);
  }

  public static String urlEncode(String value) {
    if (value == null) {
      return null;
    }
    return Base64.getUrlEncoder().withoutPadding()
        .encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }

  public static String urlDecodeToString(String value) {
    byte[] decoded = urlDecode(value);
    return decoded == null ? null : new String(decoded, StandardCharsets.UTF_8);
  }

  public static byte[] urlDecode(String value) {
    if (value == null) {
      return null;
    }
    return Base64.getUrlDecoder().decode(value);
  }
}

package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Base64UtilTest {

  @Test
  @DisplayName("Encode and decode string")
  void shouldEncodeAndDecodeString() {
    String encoded = Base64Util.encode("hello");
    assertEquals("aGVsbG8=", encoded);
    assertEquals("hello", Base64Util.decodeToString(encoded));
  }

  @Test
  @DisplayName("Encode and decode bytes")
  void shouldEncodeAndDecodeBytes() {
    byte[] bytes = "hello".getBytes(StandardCharsets.UTF_8);
    byte[] decoded = Base64Util.decode(Base64Util.encode(bytes));
    assertArrayEquals(bytes, decoded);
  }

  @Test
  @DisplayName("URL safe Base64")
  void shouldHandleUrlBase64() {
    String encoded = Base64Util.urlEncode("hello+/=");
    assertEquals("hello+/=", Base64Util.urlDecodeToString(encoded));
  }

  @Test
  @DisplayName("Return null for null input")
  void shouldReturnNullForNullInput() {
    assertNull(Base64Util.encode((String) null));
    assertNull(Base64Util.decode((String) null));
    assertNull(Base64Util.urlEncode(null));
    assertNull(Base64Util.urlDecode(null));
  }
}

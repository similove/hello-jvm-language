package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Md5UtilTest {

  @Test
  @DisplayName("Calculate MD5")
  void shouldCalculateMd5() {
    assertEquals("5d41402abc4b2a76b9719d911017c592", Md5Util.md5("hello"));
  }

  @Test
  @DisplayName("Return null for null input")
  void shouldReturnNullForNullInput() {
    assertNull(Md5Util.md5((String) null));
    assertNull(Md5Util.md5((byte[]) null));
  }
}

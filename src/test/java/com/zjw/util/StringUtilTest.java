package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilTest {

  @Test
  @DisplayName("Blank checks")
  void shouldCheckBlank() {
    assertTrue(StringUtil.isBlank(null));
    assertTrue(StringUtil.isBlank("   "));
    assertFalse(StringUtil.isNotBlank("   "));
    assertTrue(StringUtil.isNotBlank("a"));
  }

  @Test
  @DisplayName("Default and trim")
  void shouldHandleDefaultAndTrim() {
    assertEquals("", StringUtil.defaultString(null));
    assertEquals("x", StringUtil.defaultIfBlank("x", "y"));
    assertEquals("y", StringUtil.defaultIfBlank(" ", "y"));
    assertEquals("abc", StringUtil.trimToNull(" abc "));
    assertEquals("", StringUtil.trimToEmpty(null));
  }

  @Test
  @DisplayName("Join split and mask")
  void shouldJoinSplitAndMask() {
    assertEquals("a,b,c", StringUtil.join(List.of("a", "b", "c"), ","));
    assertEquals(List.of("a", "b", "c"), StringUtil.splitAndTrim("a, b, c", ","));
    assertEquals("138*****1234", StringUtil.mask("138001381234", 3, 4));
  }
}

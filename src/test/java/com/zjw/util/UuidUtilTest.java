package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UuidUtilTest {

  @Test
  @DisplayName("Generate UUID")
  void shouldGenerateUuid() {
    String uuid = UuidUtil.randomUuid();
    assertNotNull(uuid);
    assertEquals(36, uuid.length());
    assertTrue(uuid.contains("-"));
  }

  @Test
  @DisplayName("Generate compact UUID")
  void shouldGenerateCompactUuid() {
    String uuid = UuidUtil.compactUuid();
    assertNotNull(uuid);
    assertEquals(32, uuid.length());
  }
}

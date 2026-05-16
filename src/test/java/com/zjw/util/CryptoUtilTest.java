package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CryptoUtilTest {

  @Test
  @DisplayName("AES encrypt and decrypt")
  void shouldEncryptAndDecrypt() {
    String plainText = "hello-jvm-language";
    String password = "passphrase-123";

    String cipherText = CryptoUtil.aesEncrypt(plainText, password);
    assertNotNull(cipherText);
    assertNotEquals(plainText, cipherText);

    String decrypted = CryptoUtil.aesDecrypt(cipherText, password);
    assertEquals(plainText, decrypted);
  }

  @Test
  @DisplayName("Digest helpers")
  void shouldDigest() {
    assertEquals(64, CryptoUtil.sha256Hex("abc").length());
    assertEquals(32, CryptoUtil.md5Hex("abc").length());
  }

  @Test
  @DisplayName("Reject blank password")
  void shouldRejectBlankPassword() {
    assertThrows(IllegalArgumentException.class, () -> CryptoUtil.aesEncrypt("abc", " "));
  }
}

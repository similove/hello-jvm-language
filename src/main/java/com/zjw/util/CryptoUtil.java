package com.zjw.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Crypto utility methods.
 */
public final class CryptoUtil {

  private static final String AES_GCM_ALGORITHM = "AES/GCM/NoPadding";
  private static final int GCM_TAG_LENGTH = 128;
  private static final int GCM_IV_LENGTH = 12;
  private static final int AES_KEY_LENGTH = 16;
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private CryptoUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static String aesEncrypt(String plainText, String password) {
    if (plainText == null) {
      return null;
    }
    if (StringUtil.isBlank(password)) {
      throw new IllegalArgumentException("password must not be blank");
    }
    try {
      byte[] iv = new byte[GCM_IV_LENGTH];
      SECURE_RANDOM.nextBytes(iv);

      Cipher cipher = Cipher.getInstance(AES_GCM_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, buildAesKey(password), new GCMParameterSpec(GCM_TAG_LENGTH, iv));
      byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

      byte[] output = new byte[iv.length + encrypted.length];
      System.arraycopy(iv, 0, output, 0, iv.length);
      System.arraycopy(encrypted, 0, output, iv.length, encrypted.length);
      return Base64.getEncoder().encodeToString(output);
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      throw new IllegalStateException("Failed to encrypt content", e);
    }
  }

  public static String aesDecrypt(String cipherText, String password) {
    if (cipherText == null) {
      return null;
    }
    try {
      byte[] input = Base64.getDecoder().decode(cipherText);
      if (input.length <= GCM_IV_LENGTH) {
        throw new IllegalStateException("Invalid cipher text");
      }

      byte[] iv = new byte[GCM_IV_LENGTH];
      byte[] encrypted = new byte[input.length - GCM_IV_LENGTH];
      System.arraycopy(input, 0, iv, 0, GCM_IV_LENGTH);
      System.arraycopy(input, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

      Cipher cipher = Cipher.getInstance(AES_GCM_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, buildAesKey(password), new GCMParameterSpec(GCM_TAG_LENGTH, iv));
      byte[] decrypted = cipher.doFinal(encrypted);
      return new String(decrypted, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to decrypt content", e);
    }
  }

  public static String sha256Hex(String value) {
    return digestHex("SHA-256", value);
  }

  public static String md5Hex(String value) {
    return digestHex("MD5", value);
  }

  private static SecretKey buildAesKey(String password) throws Exception {
    if (StringUtil.isBlank(password)) {
      throw new IllegalArgumentException("password must not be blank");
    }
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    byte[] keyBytes = new byte[AES_KEY_LENGTH];
    System.arraycopy(hash, 0, keyBytes, 0, AES_KEY_LENGTH);
    return new SecretKeySpec(keyBytes, "AES");
  }

  private static String digestHex(String algorithm, String value) {
    if (value == null) {
      return null;
    }
    try {
      MessageDigest digest = MessageDigest.getInstance(algorithm);
      byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
      StringBuilder builder = new StringBuilder(bytes.length * 2);
      for (byte b : bytes) {
        builder.append(String.format("%02x", b));
      }
      return builder.toString();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to digest content", e);
    }
  }
}

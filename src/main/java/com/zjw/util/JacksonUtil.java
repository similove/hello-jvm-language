package com.zjw.util;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Jackson 工具类
 */
public final class JacksonUtil {

  private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

  private JacksonUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  private static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return objectMapper;
  }

  /**
   * 获取全局 ObjectMapper
   *
   * @return ObjectMapper
   */
  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  /**
   * 对象转 JSON 字符串
   *
   * @param value 待转换对象
   * @return JSON 字符串
   */
  public static String toJson(Object value) {
    if (Objects.isNull(value)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize object to JSON", e);
    }
  }

  /**
   * 对象转格式化 JSON 字符串
   *
   * @param value 待转换对象
   * @return 格式化 JSON 字符串
   */
  public static String toPrettyJson(Object value) {
    if (Objects.isNull(value)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize object to pretty JSON", e);
    }
  }

  /**
   * 对象转字节数组
   *
   * @param value 待转换对象
   * @return 字节数组
   */
  public static byte[] writeValueAsBytes(Object value) {
    if (Objects.isNull(value)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.writeValueAsBytes(value);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize object to bytes", e);
    }
  }

  /**
   * JSON 字符串转对象
   *
   * @param json JSON 字符串
   * @param clazz 目标类型
   * @param <T> 目标类型
   * @return 对象
   */
  public static <T> T parseObject(String json, Class<T> clazz) {
    if (isBlank(json) || clazz == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(json, clazz);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to parse JSON to object", e);
    }
  }

  /**
   * JSON 字符串转泛型对象
   *
   * @param json JSON 字符串
   * @param typeReference 类型引用
   * @param <T> 目标类型
   * @return 对象
   */
  public static <T> T parseObject(String json, TypeReference<T> typeReference) {
    if (isBlank(json) || typeReference == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(json, typeReference);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to parse JSON to generic object", e);
    }
  }

  /**
   * JSON 字符串转指定类型
   *
   * @param json JSON 字符串
   * @param javaType JavaType
   * @param <T> 目标类型
   * @return 对象
   */
  public static <T> T parseObject(String json, JavaType javaType) {
    if (isBlank(json) || javaType == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(json, javaType);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to parse JSON to java type", e);
    }
  }

  /**
   * JSON 字符串转数组
   *
   * @param json JSON 字符串
   * @param clazz 数组元素类型
   * @param <T> 元素类型
   * @return 数组
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] parseArray(String json, Class<T> clazz) {
    if (isBlank(json) || clazz == null) {
      return null;
    }
    try {
      JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructArrayType(clazz);
      return (T[]) OBJECT_MAPPER.readValue(json, javaType);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to parse JSON to array", e);
    }
  }

  /**
   * JSON 字符串转 List
   *
   * @param json JSON 字符串
   * @param clazz 元素类型
   * @param <T> 元素类型
   * @return List
   */
  public static <T> List<T> parseList(String json, Class<T> clazz) {
    if (isBlank(json) || clazz == null) {
      return null;
    }
    try {
      JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
      return OBJECT_MAPPER.readValue(json, javaType);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to parse JSON to list", e);
    }
  }

  /**
   * JSON 字符串转树模型
   *
   * @param json JSON 字符串
   * @return JsonNode
   */
  public static JsonNode readTree(String json) {
    if (isBlank(json)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readTree(json);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to parse JSON to tree", e);
    }
  }

  /**
   * 对象类型转换
   *
   * @param source 源对象
   * @param clazz 目标类型
   * @param <T> 目标类型
   * @return 转换后的对象
   */
  public static <T> T convertValue(Object source, Class<T> clazz) {
    if (source == null || clazz == null) {
      return null;
    }
    return OBJECT_MAPPER.convertValue(source, clazz);
  }

  /**
   * 对象类型转换
   *
   * @param source 源对象
   * @param typeReference 目标类型引用
   * @param <T> 目标类型
   * @return 转换后的对象
   */
  public static <T> T convertValue(Object source, TypeReference<T> typeReference) {
    if (source == null || typeReference == null) {
      return null;
    }
    return OBJECT_MAPPER.convertValue(source, typeReference);
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }
}

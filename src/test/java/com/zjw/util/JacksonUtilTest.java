package com.zjw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JacksonUtilTest {

  @Test
  @DisplayName("Serialize and deserialize object")
  void shouldSerializeAndDeserializeObject() {
    TestUser user = new TestUser();
    user.setId(1L);
    user.setName("alice");

    String json = JacksonUtil.toJson(user);
    assertNotNull(json);

    TestUser result = JacksonUtil.parseObject(json, TestUser.class);
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("alice", result.getName());
  }

  @Test
  @DisplayName("Handle generic type")
  void shouldHandleGenericType() {
    String json = "[{\"id\":1,\"name\":\"alice\"},{\"id\":2,\"name\":\"bob\"}]";

    List<TestUser> users = JacksonUtil.parseObject(
        json,
        new com.fasterxml.jackson.core.type.TypeReference<List<TestUser>>() {});
    assertNotNull(users);
    assertEquals(2, users.size());
    assertEquals("bob", users.get(1).getName());
  }

  @Test
  @DisplayName("Convert object value")
  void shouldConvertValue() {
    Map<String, Object> source = Map.of("id", 3L, "name", "carol");

    TestUser result = JacksonUtil.convertValue(source, TestUser.class);
    assertNotNull(result);
    assertEquals(3L, result.getId());
    assertEquals("carol", result.getName());
  }

  @Test
  @DisplayName("Serialize to bytes")
  void shouldSerializeToBytes() {
    TestUser user = new TestUser();
    user.setId(4L);
    user.setName("david");

    byte[] bytes = JacksonUtil.writeValueAsBytes(user);
    assertNotNull(bytes);

    String json = new String(bytes, StandardCharsets.UTF_8);
    assertTrue(json.contains("david"));
  }

  @Test
  @DisplayName("Parse array")
  void shouldParseArray() {
    String json = "[{\"id\":1,\"name\":\"alice\"},{\"id\":2,\"name\":\"bob\"}]";

    TestUser[] users = JacksonUtil.parseArray(json, TestUser.class);
    assertNotNull(users);
    assertEquals(2, users.length);
    assertEquals("alice", users[0].getName());
  }

  @Test
  @DisplayName("Parse list")
  void shouldParseList() {
    String json = "[{\"id\":1,\"name\":\"alice\"},{\"id\":2,\"name\":\"bob\"}]";

    List<TestUser> users = JacksonUtil.parseList(json, TestUser.class);
    assertNotNull(users);
    assertEquals(2, users.size());
    assertEquals(2L, users.get(1).getId());
  }

  @Test
  @DisplayName("Read tree")
  void shouldReadTree() {
    JsonNode node = JacksonUtil.readTree("{\"id\":5,\"name\":\"eve\"}");
    assertNotNull(node);
    assertEquals(5, node.get("id").asInt());
    assertEquals("eve", node.get("name").asText());
  }

  @Test
  @DisplayName("Return null for empty input")
  void shouldReturnNullForEmptyInput() {
    assertNull(JacksonUtil.toJson(null));
    assertNull(JacksonUtil.writeValueAsBytes(null));
    assertNull(JacksonUtil.parseObject(null, TestUser.class));
    assertNull(JacksonUtil.parseArray(null, TestUser.class));
    assertNull(JacksonUtil.parseList(null, TestUser.class));
    assertNull(JacksonUtil.readTree(null));
    assertNull(JacksonUtil.convertValue(null, TestUser.class));
  }

  public static class TestUser {
    private Long id;
    private String name;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}

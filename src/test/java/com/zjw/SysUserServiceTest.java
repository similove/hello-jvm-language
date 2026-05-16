package com.zjw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjw.entity.SysUser;
import com.zjw.mapper.master.SysUserMapper;
import com.zjw.service.SysUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SysUser 单元测试 (Java)
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SysUserServiceTest {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysUserMapper sysUserMapper;

  private static Long testUserId;
  private static final String TEST_USERNAME = "test_user_java_" + System.currentTimeMillis();

  @Test
  @Order(1)
  @DisplayName("测试新增用户")
  void testSaveUser() {
    SysUser user = new SysUser();
    user.setUsername(TEST_USERNAME);
    user.setAge(25);
    user.setEmail("test_java@test.com");

    boolean result = sysUserService.saveUser(user);
    assertTrue(result, "新增用户应该成功");

    testUserId = user.getId();
    assertNotNull(testUserId, "用户ID不应该为空");
    System.out.println("新增用户成功, ID: " + testUserId);
  }

  @Test
  @Order(2)
  @DisplayName("测试根据ID查询用户")
  void testGetUserById() {
    assertNotNull(testUserId, "用户ID不应该为空");

    SysUser user = sysUserService.getUserById(testUserId);
    assertNotNull(user, "根据ID查询用户不应该为空");
    assertEquals(TEST_USERNAME, user.getUsername(), "用户名应该匹配");
    System.out.println("根据ID查询用户成功: " + user);
  }

  @Test
  @Order(3)
  @DisplayName("测试根据用户名查询用户")
  void testGetUserByUsername() {
    SysUser user = sysUserService.getUserByUsername(TEST_USERNAME);
    assertNotNull(user, "根据用户名查询用户不应该为空");
    assertEquals(TEST_USERNAME, user.getUsername(), "用户名应该匹配");
    System.out.println("根据用户名查询用户成功: " + user);
  }

  @Test
  @Order(4)
  @DisplayName("测试更新用户")
  void testUpdateUser() {
    assertNotNull(testUserId, "用户ID不应该为空");

    SysUser user = sysUserService.getUserById(testUserId);
    user.setAge(30);
    user.setEmail("updated_java@test.com");

    boolean result = sysUserService.updateUser(user);
    assertTrue(result, "更新用户应该成功");

    SysUser updated = sysUserService.getUserById(testUserId);
    assertEquals(30, updated.getAge(), "年龄应该更新为30");
    assertEquals("updated_java@test.com", updated.getEmail(), "邮箱应该更新");
    System.out.println("更新用户成功: " + updated);
  }

  @Test
  @Order(5)
  @DisplayName("测试查询所有用户")
  void testListUsers() {
    List<SysUser> users = sysUserService.listUsers();
    assertNotNull(users, "用户列表不应该为空");
    assertTrue(users.size() > 0, "用户列表应该包含数据");
    System.out.println("查询所有用户成功, 共 " + users.size() + " 条");
  }

  @Test
  @Order(6)
  @DisplayName("测试分页查询用户")
  void testPageUsers() {
    IPage<SysUser> page1 = sysUserService.pageUsers(1, 3);
    assertNotNull(page1, "分页查询不应该返回空");
    assertTrue(page1.getRecords().size() <= 3, "每页应该最多3条数据");

    IPage<SysUser> page2 = sysUserService.pageUsers(2, 3);
    assertNotNull(page2, "分页查询不应该返回空");

    long page1Size = page1.getRecords().size();
    long page2Size = page2.getRecords().size();
    System.out.println("分页查询成功, 第1页: " + page1Size + " 条, 第2页: " + page2Size + " 条");
  }

  @Test
  @Order(7)
  @DisplayName("测试删除用户")
  void testDeleteUser() {
    assertNotNull(testUserId, "用户ID不应该为空");

    boolean result = sysUserService.deleteUser(testUserId);
    assertTrue(result, "删除用户应该成功");

    SysUser deleted = sysUserService.getUserById(testUserId);
    assertNull(deleted, "删除后查询应该返回空");
    System.out.println("删除用户成功, ID: " + testUserId);
  }
}

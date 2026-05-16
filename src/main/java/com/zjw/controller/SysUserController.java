package com.zjw.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjw.entity.SysUser;
import com.zjw.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制器
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

  private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

  private final SysUserService sysUserService;

  public SysUserController(SysUserService sysUserService) {
    this.sysUserService = sysUserService;
  }

  /**
   * 分页查询用户
   *
   * @param pageNum 页码
   * @param pageSize 每页数量
   * @return 分页结果
   */
  @GetMapping("/page")
  public ResponseEntity<IPage<SysUser>> pageUsers(
      @RequestParam(defaultValue = "1") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    log.info("pageUsers 入参: pageNum={}, pageSize={}", pageNum, pageSize);
    long startTime = System.currentTimeMillis();
    try {
      IPage<SysUser> page = sysUserService.pageUsers(pageNum, pageSize);
      ResponseEntity<IPage<SysUser>> result = ResponseEntity.ok(page);
      long costTime = System.currentTimeMillis() - startTime;
      log.info("pageUsers 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("pageUsers 异常", e);
      throw e;
    }
  }

  /**
   * 查询所有用户
   *
   * @return 用户列表
   */
  @GetMapping("/list")
  public ResponseEntity<List<SysUser>> listUsers() {
    log.info("listUsers 入参: 无");
    long startTime = System.currentTimeMillis();
    try {
      ResponseEntity<List<SysUser>> result = ResponseEntity.ok(sysUserService.listUsers());
      long costTime = System.currentTimeMillis() - startTime;
      log.info("listUsers 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("listUsers 异常", e);
      throw e;
    }
  }

  /**
   * 根据ID查询用户
   *
   * @param id 用户ID
   * @return 用户信息
   */
  @GetMapping("/{id}")
  public ResponseEntity<SysUser> getUserById(@PathVariable Long id) {
    log.info("getUserById 入参: id={}", id);
    long startTime = System.currentTimeMillis();
    try {
      SysUser user = sysUserService.getUserById(id);
      ResponseEntity<SysUser> result;
      if (user == null) {
        result = ResponseEntity.notFound().build();
      } else {
        result = ResponseEntity.ok(user);
      }
      long costTime = System.currentTimeMillis() - startTime;
      log.info("getUserById 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("getUserById 异常", e);
      throw e;
    }
  }

  /**
   * 根据用户名查询用户
   *
   * @param username 用户名
   * @return 用户信息
   */
  @GetMapping("/username/{username}")
  public ResponseEntity<SysUser> getUserByUsername(@PathVariable String username) {
    log.info("getUserByUsername 入参: username={}", username);
    long startTime = System.currentTimeMillis();
    try {
      SysUser user = sysUserService.getUserByUsername(username);
      ResponseEntity<SysUser> result;
      if (user == null) {
        result = ResponseEntity.notFound().build();
      } else {
        result = ResponseEntity.ok(user);
      }
      long costTime = System.currentTimeMillis() - startTime;
      log.info("getUserByUsername 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("getUserByUsername 异常", e);
      throw e;
    }
  }

  /**
   * 新增用户
   *
   * @param user 用户信息
   * @return 操作结果
   */
  @PostMapping
  public ResponseEntity<String> saveUser(@RequestBody SysUser user) {
    log.info("saveUser 入参: user={}", user);
    long startTime = System.currentTimeMillis();
    try {
      boolean success = sysUserService.saveUser(user);
      ResponseEntity<String> result;
      if (success) {
        result = ResponseEntity.ok("用户创建成功");
      } else {
        result = ResponseEntity.badRequest().body("用户创建失败");
      }
      long costTime = System.currentTimeMillis() - startTime;
      log.info("saveUser 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("saveUser 异常", e);
      throw e;
    }
  }

  /**
   * 修改用户
   *
   * @param id 用户ID
   * @param user 用户信息
   * @return 操作结果
   */
  @PutMapping("/{id}")
  public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody SysUser user) {
    log.info("updateUser 入参: id={}, user={}", id, user);
    long startTime = System.currentTimeMillis();
    try {
      user.setId(id);
      boolean success = sysUserService.updateUser(user);
      ResponseEntity<String> result;
      if (success) {
        result = ResponseEntity.ok("用户更新成功");
      } else {
        result = ResponseEntity.badRequest().body("用户更新失败");
      }
      long costTime = System.currentTimeMillis() - startTime;
      log.info("updateUser 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("updateUser 异常", e);
      throw e;
    }
  }

  /**
   * 删除用户
   *
   * @param id 用户ID
   * @return 操作结果
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    log.info("deleteUser 入参: id={}", id);
    long startTime = System.currentTimeMillis();
    try {
      boolean success = sysUserService.deleteUser(id);
      ResponseEntity<String> result;
      if (success) {
        result = ResponseEntity.ok("用户删除成功");
      } else {
        result = ResponseEntity.badRequest().body("用户删除失败");
      }
      long costTime = System.currentTimeMillis() - startTime;
      log.info("deleteUser 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("deleteUser 异常", e);
      throw e;
    }
  }
}

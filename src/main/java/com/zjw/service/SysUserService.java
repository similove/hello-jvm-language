package com.zjw.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjw.entity.SysUser;

/**
 * 系统用户服务接口
 */
public interface SysUserService {

  /**
   * 分页查询用户
   *
   * @param pageNum 页码
   * @param pageSize 每页数量
   * @return 分页结果
   */
  IPage<SysUser> pageUsers(int pageNum, int pageSize);

  /**
   * 查询所有用户
   *
   * @return 用户列表
   */
  List<SysUser> listUsers();

  /**
   * 根据ID查询用户
   *
   * @param id 用户ID
   * @return 用户信息
   */
  SysUser getUserById(Long id);

  /**
   * 根据用户名查询用户
   *
   * @param username 用户名
   * @return 用户信息
   */
  SysUser getUserByUsername(String username);

  /**
   * 新增用户
   *
   * @param user 用户信息
   * @return 是否成功
   */
  boolean saveUser(SysUser user);

  /**
   * 修改用户
   *
   * @param user 用户信息
   * @return 是否成功
   */
  boolean updateUser(SysUser user);

  /**
   * 删除用户
   *
   * @param id 用户ID
   * @return 是否成功
   */
  boolean deleteUser(Long id);
}

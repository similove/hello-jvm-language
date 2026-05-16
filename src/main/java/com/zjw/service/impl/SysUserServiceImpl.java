package com.zjw.service.impl;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjw.entity.SysUser;
import com.zjw.mapper.master.SysUserMapper;
import com.zjw.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务实现
 */
@Service
public class SysUserServiceImpl implements SysUserService {

  private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

  private final SysUserMapper sysUserMapper;

  public SysUserServiceImpl(SysUserMapper sysUserMapper) {
    this.sysUserMapper = sysUserMapper;
  }

  @Override
  @DS("master")
  public IPage<SysUser> pageUsers(int pageNum, int pageSize) {
    log.info("pageUsers 入参: pageNum={}, pageSize={}", pageNum, pageSize);
    long startTime = System.currentTimeMillis();
    try {
      Page<SysUser> page = new Page<>(pageNum, pageSize);
      IPage<SysUser> result = sysUserMapper.selectPage(page, null);
      long costTime = System.currentTimeMillis() - startTime;
      log.info("pageUsers 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("pageUsers 异常", e);
      throw e;
    }
  }

  @Override
  @DS("master")
  public List<SysUser> listUsers() {
    log.info("listUsers 入参: 无");
    long startTime = System.currentTimeMillis();
    try {
      List<SysUser> result = sysUserMapper.selectList(null);
      long costTime = System.currentTimeMillis() - startTime;
      log.info("listUsers 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("listUsers 异常", e);
      throw e;
    }
  }

  @Override
  @DS("master")
  public SysUser getUserById(Long id) {
    log.info("getUserById 入参: id={}", id);
    long startTime = System.currentTimeMillis();
    try {
      SysUser result = sysUserMapper.selectById(id);
      long costTime = System.currentTimeMillis() - startTime;
      log.info("getUserById 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("getUserById 异常", e);
      throw e;
    }
  }

  @Override
  @DS("master")
  public SysUser getUserByUsername(String username) {
    log.info("getUserByUsername 入参: username={}", username);
    long startTime = System.currentTimeMillis();
    try {
      LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(SysUser::getUsername, username);
      SysUser result = sysUserMapper.selectOne(wrapper);
      long costTime = System.currentTimeMillis() - startTime;
      log.info("getUserByUsername 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("getUserByUsername 异常", e);
      throw e;
    }
  }

  @Override
  @DS("master")
  public boolean saveUser(SysUser user) {
    log.info("saveUser 入参: user={}", user);
    long startTime = System.currentTimeMillis();
    try {
      boolean result = sysUserMapper.insert(user) > 0;
      long costTime = System.currentTimeMillis() - startTime;
      log.info("saveUser 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("saveUser 异常", e);
      throw e;
    }
  }

  @Override
  @DS("master")
  public boolean updateUser(SysUser user) {
    log.info("updateUser 入参: user={}", user);
    long startTime = System.currentTimeMillis();
    try {
      boolean result = sysUserMapper.updateById(user) > 0;
      long costTime = System.currentTimeMillis() - startTime;
      log.info("updateUser 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("updateUser 异常", e);
      throw e;
    }
  }

  @Override
  @DS("master")
  public boolean deleteUser(Long id) {
    log.info("deleteUser 入参: id={}", id);
    long startTime = System.currentTimeMillis();
    try {
      boolean result = sysUserMapper.deleteById(id) > 0;
      long costTime = System.currentTimeMillis() - startTime;
      log.info("deleteUser 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("deleteUser 异常", e);
      throw e;
    }
  }
}

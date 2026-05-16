package com.zjw.mapper.master;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjw.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Mapper (MySQL 主库)
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}

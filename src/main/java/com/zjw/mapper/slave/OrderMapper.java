package com.zjw.mapper.slave;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjw.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单 Mapper (PostgreSQL 从库)
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

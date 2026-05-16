package com.zjw.service.impl;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zjw.entity.Order;
import com.zjw.mapper.slave.OrderMapper;
import com.zjw.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 订单服务实现
 */
@Service
public class OrderServiceImpl implements OrderService {

  private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

  private final OrderMapper orderMapper;

  public OrderServiceImpl(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Override
  @DS("slave")
  public List<Order> listOrders() {
    log.info("listOrders 入参: 无");
    long startTime = System.currentTimeMillis();
    try {
      List<Order> result = orderMapper.selectList(null);
      long costTime = System.currentTimeMillis() - startTime;
      log.info("listOrders 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("listOrders 异常", e);
      throw e;
    }
  }
}

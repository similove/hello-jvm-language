package com.zjw.service;

import java.util.List;

import com.zjw.entity.Order;

/**
 * 订单服务接口
 */
public interface OrderService {

  /**
   * 查询所有订单
   *
   * @return 订单列表
   */
  List<Order> listOrders();
}

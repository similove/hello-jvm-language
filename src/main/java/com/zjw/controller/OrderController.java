package com.zjw.controller;

import java.util.List;

import com.zjw.entity.Order;
import com.zjw.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/order")
public class OrderController {

  private static final Logger log = LoggerFactory.getLogger(OrderController.class);

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * 查询所有订单 (PostgreSQL 从库)
   *
   * @return 订单列表
   */
  @GetMapping("/list")
  public List<Order> listOrders() {
    log.info("listOrders 入参: 无");
    long startTime = System.currentTimeMillis();
    try {
      List<Order> result = orderService.listOrders();
      long costTime = System.currentTimeMillis() - startTime;
      log.info("listOrders 返回: result={}, 耗时: {}ms", result, costTime);
      return result;
    } catch (Exception e) {
      log.error("listOrders 异常", e);
      throw e;
    }
  }
}

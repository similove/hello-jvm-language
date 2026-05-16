package com.zjw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 订单表 (PostgreSQL)
 */
@TableName("\"order\"")
public class Order {

  @TableId(type = IdType.AUTO)
  private Long id;

  @TableField("order_sin")
  private String orderSin;

  @TableField("order_desc")
  private String orderDesc;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderSin() {
    return orderSin;
  }

  public void setOrderSin(String orderSin) {
    this.orderSin = orderSin;
  }

  public String getOrderDesc() {
    return orderDesc;
  }

  public void setOrderDesc(String orderDesc) {
    this.orderDesc = orderDesc;
  }
}

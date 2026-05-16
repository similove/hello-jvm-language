-- =============================================
-- MySQL 数据库初始化脚本
-- 数据库: test
-- 表: sys_user (系统用户表)
-- =============================================

-- 创建数据库 (如果不存在)
-- CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- 使用数据库
USE test;

-- ---------------------------------------------
-- 创建系统用户表
-- ---------------------------------------------
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键自增',
  `username` varchar(50) NOT NULL COMMENT '用户名，唯一',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `age` int DEFAULT '0' COMMENT '年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';

-- ---------------------------------------------
-- 插入测试数据
-- ---------------------------------------------
INSERT INTO `sys_user` (`username`, `email`, `age`) VALUES
  ('admin', 'admin@example.com', 30),
  ('zhangsan', 'zhangsan@example.com', 25),
  ('lisi', 'lisi@example.com', 28),
  ('wangwu', 'wangwu@example.com', 22),
  ('zhaoliu', 'zhaoliu@example.com', 35);

-- ---------------------------------------------
-- 查询验证
-- ---------------------------------------------
SELECT '=== MySQL: sys_user 表数据 ===' AS '';
SELECT * FROM sys_user;

-- =============================================
-- PostgreSQL 数据库初始化脚本
-- 数据库: test
-- 表: order (订单表)
-- =============================================

-- 创建数据库 (如果不存在)
-- CREATE DATABASE test;

-- ---------------------------------------------
-- 创建订单表 (注意: order 是 SQL 关键字，需要用双引号包裹)
-- ---------------------------------------------
DROP TABLE IF EXISTS public."order";

CREATE TABLE public."order" (
    id bigint NOT NULL DEFAULT nextval('order_id_seq'::regclass) COMMENT '订单ID，主键自增',
    order_sin varchar(255) DEFAULT NULL COMMENT '订单编号',
    order_desc varchar(255) DEFAULT NULL COMMENT '订单描述',
    CONSTRAINT order_pkey PRIMARY KEY (id)
);

-- ---------------------------------------------
-- 创建序列 (如果使用自增)
-- ---------------------------------------------
DROP SEQUENCE IF EXISTS order_id_seq;
CREATE SEQUENCE order_id_seq START WITH 1 INCREMENT BY 1;

-- 绑定序列到 id 字段
ALTER TABLE public."order" ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);

-- ---------------------------------------------
-- 插入测试数据
-- ---------------------------------------------
INSERT INTO public."order" (order_sin, order_desc) VALUES
  ('ORD-20250615001', '测试订单001'),
  ('ORD-20250615002', '测试订单002'),
  ('ORD-20250615003', '测试订单003'),
  ('ORD-20250615004', '测试订单004'),
  ('ORD-20250615005', '测试订单005');

-- ---------------------------------------------
-- 查询验证
-- ---------------------------------------------
SELECT '=== PostgreSQL: order 表数据 ===' AS '';
SELECT * FROM public."order";

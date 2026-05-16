-- PostgreSQL person_info 表初始化脚本
-- 数据库: postgres test 库

-- 创建表
CREATE TABLE IF NOT EXISTS person_info (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT
);

-- 插入测试数据
INSERT INTO person_info (id, name, age) VALUES
    (1, '张三', 25),
    (2, '李四', 30),
    (3, '王五', 28),
    (4, '赵六', 35),
    (5, '钱七', 22),
    (6, '孙八', 27),
    (7, '周九', 31),
    (8, '吴十', 29),
    (9, '郑十一', 33),
    (10, '王十二', 26)
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    age = EXCLUDED.age;

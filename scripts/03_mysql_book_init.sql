-- MySQL book 表初始化脚本
-- 数据库: mysql test 库

-- 创建表
CREATE TABLE IF NOT EXISTS book (
    id BIGINT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    `desc` VARCHAR(255)
);

-- 插入测试数据
INSERT INTO book (id, name, `desc`) VALUES
    (1, '深入理解JVM虚拟机', '周志明 著，讲解JVM原理'),
    (2, 'Effective Java', 'Joshua Bloch 著，Java最佳实践'),
    (3, '算法导论', 'Thomas H. Cormen 著，经典算法书籍'),
    (4, '计算机网络：自顶向下方法', 'James F. Kurose 著'),
    (5, 'Spring Boot实战', 'Craig Walls 著，Spring Boot指南'),
    (6, 'Kotlin实战', 'Dmitry Jemerov 著，Kotlin官方指南'),
    (7, 'Scala编程', 'Martin Odersky 著，Scala权威指南'),
    (8, 'Groovy程序设计', 'Ken Kousen 著，Groovy入门指南'),
    (9, 'MySQL必知必会', 'Ben Forta 著，MySQL基础'),
    (10, 'Redis设计与实现', '黄健宏 著，Redis内部设计')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    `desc` = VALUES(`desc`);

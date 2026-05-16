-- PostgreSQL address 表初始化脚本
-- 数据库: postgres test 库

-- 创建表
CREATE TABLE IF NOT EXISTS address (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    detail VARCHAR(255),
    "desc" VARCHAR(255)
);

-- 插入测试数据
INSERT INTO address (id, name, detail, "desc") VALUES
    (1, '北京市', '朝阳区建国路88号', '主要办公地点'),
    (2, '上海市', '浦东新区世纪大道100号', '分公司地址'),
    (3, '广州市', '天河区珠江新城', '研发中心'),
    (4, '深圳市', '南山区科技园', '分部办公室'),
    (5, '杭州市', '西湖区文三路90号', '电商基地'),
    (6, '成都市', '高新区天府大道', '西部中心'),
    (7, '武汉市', '光谷大道光谷广场', '中部基地'),
    (8, '南京市', '建邺区河西大街', '华东分部'),
    (9, '西安市', '高新区科技路', '西北中心'),
    (10, '重庆市', '渝北区新南路', '西南总部')
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    detail = EXCLUDED.detail,
    "desc" = EXCLUDED."desc";

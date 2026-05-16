package com.zjw.mapper

import com.baomidou.dynamic.datasource.annotation.DS
import com.zjw.entity.Address
import org.apache.ibatis.annotations.Mapper

/**
 * 地址 Mapper (PostgreSQL 从库)
 */
@Mapper
@DS("slave")
interface AddressMapper {

    /**
     * 查询所有地址
     */
    List<Address> selectList()

    /**
     * 分页查询
     * @param pageSize 每页数量
     * @param offset 偏移量
     * @return 地址列表
     */
    List<Address> selectPage(int pageSize, int offset)

    /**
     * 根据ID查询
     */
    Address selectById(int id)

    /**
     * 根据姓名查询
     */
    Address selectByName(String name)

    /**
     * 插入数据
     */
    int insert(Address address)

    /**
     * 更新数据
     */
    int update(Address address)

    /**
     * 删除数据
     */
    int delete(int id)

    /**
     * 查询总数
     */
    long count()
}

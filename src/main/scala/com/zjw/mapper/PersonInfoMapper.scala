package com.zjw.mapper

import com.baomidou.dynamic.datasource.annotation.DS
import com.zjw.entity.PersonInfo
import org.apache.ibatis.annotations.Mapper

import java.util.List

/**
 * 人员信息 Mapper (PostgreSQL 从库)
 */
@Mapper
@DS("slave")
trait PersonInfoMapper {

    /**
     * 查询所有人员
     */
    def selectList(): List[PersonInfo]

    /**
     * 分页查询
     * @param pageSize 每页数量
     * @param offset 偏移量
     * @return 人员列表
     */
    def selectPage(pageSize: Int, offset: Int): List[PersonInfo]

    /**
     * 根据ID查询
     */
    def selectById(id: Int): PersonInfo

    /**
     * 根据姓名查询
     */
    def selectByName(name: String): PersonInfo

    /**
     * 插入数据
     */
    def insert(person: PersonInfo): Int

    /**
     * 更新数据
     */
    def update(person: PersonInfo): Int

    /**
     * 删除数据
     */
    def delete(id: Int): Int

    /**
     * 查询总数
     */
    def count(): Long
}

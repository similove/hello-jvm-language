package com.zjw.mapper

import com.baomidou.dynamic.datasource.annotation.DS
import com.zjw.entity.Book
import org.apache.ibatis.annotations.Mapper

/**
 * 图书 Mapper (MySQL 主库)
 */
@Mapper
@DS("master")
interface BookMapper {
    /**
     * 查询所有图书
     */
    fun selectList(): List<Book>

    /**
     * 分页查询
     * @param pageSize 每页数量
     * @param offset 偏移量
     * @return 图书列表
     */
    fun selectPage(
        pageSize: Int,
        offset: Int,
    ): List<Book>

    /**
     * 根据ID查询
     */
    fun selectById(id: Long): Book

    /**
     * 根据书名查询
     */
    fun selectByName(name: String): Book

    /**
     * 插入数据
     */
    fun insert(book: Book): Int

    /**
     * 更新数据
     */
    fun update(book: Book): Int

    /**
     * 删除数据
     */
    fun delete(id: Long): Int

    /**
     * 查询总数
     */
    fun count(): Long
}

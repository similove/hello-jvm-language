package com.zjw.service

import com.zjw.entity.Book

/**
 * 图书服务接口
 */
interface BookService {
    /**
     * 查询所有图书
     */
    fun listBooks(): List<Book>

    /**
     * 分页查询
     * @param page 页码 (从1开始)
     * @param pageSize 每页数量
     * @return 图书列表
     */
    fun pageBooks(
        page: Int,
        pageSize: Int,
    ): List<Book>

    /**
     * 根据ID查询
     */
    fun getBookById(id: Long): Book

    /**
     * 根据书名查询
     */
    fun getBookByName(name: String): Book

    /**
     * 新增图书
     */
    fun saveBook(book: Book): Boolean

    /**
     * 更新图书
     */
    fun updateBook(book: Book): Boolean

    /**
     * 删除图书
     */
    fun deleteBook(id: Long): Boolean

    /**
     * 查询总数
     */
    fun getTotalCount(): Long
}

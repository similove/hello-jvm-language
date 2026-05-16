package com.zjw.service.impl

import com.zjw.entity.Book
import com.zjw.mapper.BookMapper
import com.zjw.service.BookService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 图书服务实现 (MySQL 主库)
 */
@Service
class BookServiceImpl(
    private val bookMapper: BookMapper,
) : BookService {
    private val log = LoggerFactory.getLogger(BookServiceImpl::class.java)

    override fun listBooks(): List<Book> {
        val startTime = System.currentTimeMillis()
        log.info("listBooks 入参: 无")
        val result = bookMapper.selectList()
        val costTime = System.currentTimeMillis() - startTime
        log.info("listBooks 返回: result.size=${result.size}, 耗时: ${costTime}ms")
        return result
    }

    override fun pageBooks(
        page: Int,
        pageSize: Int,
    ): List<Book> {
        val startTime = System.currentTimeMillis()
        log.info("pageBooks 入参: page=$page, pageSize=$pageSize")
        val offset = (page - 1) * pageSize
        val result = bookMapper.selectPage(pageSize, offset)
        val costTime = System.currentTimeMillis() - startTime
        log.info("pageBooks 返回: result.size=${result.size}, 耗时: ${costTime}ms")
        return result
    }

    override fun getBookById(id: Long): Book {
        val startTime = System.currentTimeMillis()
        log.info("getBookById 入参: id=$id")
        val result = bookMapper.selectById(id)
        val costTime = System.currentTimeMillis() - startTime
        log.info("getBookById 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    override fun getBookByName(name: String): Book {
        val startTime = System.currentTimeMillis()
        log.info("getBookByName 入参: name=$name")
        val result = bookMapper.selectByName(name)
        val costTime = System.currentTimeMillis() - startTime
        log.info("getBookByName 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    override fun saveBook(book: Book): Boolean {
        val startTime = System.currentTimeMillis()
        log.info("saveBook 入参: book=$book")
        val result = bookMapper.insert(book) > 0
        val costTime = System.currentTimeMillis() - startTime
        log.info("saveBook 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    override fun updateBook(book: Book): Boolean {
        val startTime = System.currentTimeMillis()
        log.info("updateBook 入参: book=$book")
        val result = bookMapper.update(book) > 0
        val costTime = System.currentTimeMillis() - startTime
        log.info("updateBook 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    override fun deleteBook(id: Long): Boolean {
        val startTime = System.currentTimeMillis()
        log.info("deleteBook 入参: id=$id")
        val result = bookMapper.delete(id) > 0
        val costTime = System.currentTimeMillis() - startTime
        log.info("deleteBook 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    override fun getTotalCount(): Long {
        val startTime = System.currentTimeMillis()
        log.info("getTotalCount 入参: 无")
        val result = bookMapper.count()
        val costTime = System.currentTimeMillis() - startTime
        log.info("getTotalCount 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }
}

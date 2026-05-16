package com.zjw.controller

import com.zjw.entity.Book
import com.zjw.service.BookService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 图书控制器 (Kotlin)
 */
@RestController
@RequestMapping(value = ["/book"])
class BookController {
    private val log = LoggerFactory.getLogger(BookController::class.java)

    @Autowired
    private lateinit var bookService: BookService

    /**
     * 查询所有图书
     */
    @GetMapping(value = ["/list"])
    fun list(): List<Book> {
        val startTime = System.currentTimeMillis()
        log.info("list 入参: 无")
        val result = bookService.listBooks()
        val costTime = System.currentTimeMillis() - startTime
        log.info("list 返回: result.size=${result.size}, 耗时: ${costTime}ms")
        return result
    }

    /**
     * 分页查询
     * @param page 页码 (默认1)
     * @param pageSize 每页数量 (默认10)
     */
    @GetMapping(value = ["/page"])
    fun page(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): Map<String, Any> {
        val startTime = System.currentTimeMillis()
        log.info("page 入参: page=$page, pageSize=$pageSize")
        val list = bookService.pageBooks(page, pageSize)
        val total = bookService.getTotalCount()
        val result =
            mapOf(
                "list" to list,
                "total" to total,
                "page" to page,
                "pageSize" to pageSize,
            )
        val costTime = System.currentTimeMillis() - startTime
        log.info("page 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    /**
     * 根据ID查询
     */
    @GetMapping(value = ["/{id}"])
    fun getById(
        @PathVariable id: Long,
    ): Book {
        val startTime = System.currentTimeMillis()
        log.info("getById 入参: id=$id")
        val result = bookService.getBookById(id)
        val costTime = System.currentTimeMillis() - startTime
        log.info("getById 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    /**
     * 根据书名查询
     */
    @GetMapping(value = ["/name/{name}"])
    fun getByName(
        @PathVariable name: String,
    ): Book {
        val startTime = System.currentTimeMillis()
        log.info("getByName 入参: name=$name")
        val result = bookService.getBookByName(name)
        val costTime = System.currentTimeMillis() - startTime
        log.info("getByName 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    /**
     * 新增图书
     */
    @PostMapping
    fun save(
        @RequestBody book: Book,
    ): Map<String, Any> {
        val startTime = System.currentTimeMillis()
        log.info("save 入参: book=$book")
        val success = bookService.saveBook(book)
        val result =
            mapOf(
                "success" to success,
                "message" to if (success) "新增成功" else "新增失败",
            )
        val costTime = System.currentTimeMillis() - startTime
        log.info("save 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    /**
     * 更新图书
     */
    @PutMapping(value = ["/{id}"])
    fun update(
        @PathVariable id: Long,
        @RequestBody book: Book,
    ): Map<String, Any> {
        val startTime = System.currentTimeMillis()
        log.info("update 入参: id=$id, book=$book")
        book.id = id
        val success = bookService.updateBook(book)
        val result =
            mapOf(
                "success" to success,
                "message" to if (success) "更新成功" else "更新失败",
            )
        val costTime = System.currentTimeMillis() - startTime
        log.info("update 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    /**
     * 删除图书
     */
    @DeleteMapping(value = ["/{id}"])
    fun delete(
        @PathVariable id: Long,
    ): Map<String, Any> {
        val startTime = System.currentTimeMillis()
        log.info("delete 入参: id=$id")
        val success = bookService.deleteBook(id)
        val result =
            mapOf(
                "success" to success,
                "message" to if (success) "删除成功" else "删除失败",
            )
        val costTime = System.currentTimeMillis() - startTime
        log.info("delete 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }
}

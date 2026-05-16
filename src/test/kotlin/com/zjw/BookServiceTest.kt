package com.zjw

import com.zjw.entity.Book
import com.zjw.mapper.BookMapper
import com.zjw.service.BookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Book 单元测试 (Kotlin)
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServiceTest {
    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var bookMapper: BookMapper

    companion object {
        var testId: Long = 0
        var testName: String = "test_book_kotlin_${System.currentTimeMillis()}"
    }

    @Test
    @Order(1)
    @DisplayName("测试新增图书")
    fun testSaveBook() {
        val book = Book()
        book.id = testId
        book.name = testName
        book.desc = "测试描述"

        val result = bookService.saveBook(book)
        assertTrue(result, "新增图书应该成功")

        testId = book.id
        assertTrue(testId != 0L, "图书ID不应该为空")
        println("新增图书成功, ID: $testId")
    }

    @Test
    @Order(2)
    @DisplayName("测试根据ID查询图书")
    fun testGetBookById() {
        assertTrue(testId != 0L, "图书ID不应该为空")

        val book = bookService.getBookById(testId)
        assertNotNull(book, "根据ID查询图书不应该为空")
        assertEquals(testName, book.name, "书名应该匹配")
        println("根据ID查询图书成功: $book")
    }

    @Test
    @Order(3)
    @DisplayName("测试根据书名查询图书")
    fun testGetBookByName() {
        val book = bookService.getBookByName(testName)
        assertNotNull(book, "根据书名查询图书不应该为空")
        assertEquals(testName, book.name, "书名应该匹配")
        println("根据书名查询图书成功: $book")
    }

    @Test
    @Order(4)
    @DisplayName("测试更新图书")
    fun testUpdateBook() {
        assertTrue(testId != 0L, "图书ID不应该为空")

        val book = bookService.getBookById(testId)
        book.desc = "更新后的描述"

        val result = bookService.updateBook(book)
        assertTrue(result, "更新图书应该成功")

        val updated = bookService.getBookById(testId)
        assertEquals("更新后的描述", updated.desc, "描述应该更新")
        println("更新图书成功: $updated")
    }

    @Test
    @Order(5)
    @DisplayName("测试查询所有图书")
    fun testListBooks() {
        val books = bookService.listBooks()
        assertNotNull(books, "图书列表不应该为空")
        assertTrue(books.size > 0, "图书列表应该包含数据")
        println("查询所有图书成功, 共 ${books.size} 条")
    }

    @Test
    @Order(6)
    @DisplayName("测试分页查询图书")
    fun testPageBooks() {
        val page1 = bookService.pageBooks(1, 3)
        assertNotNull(page1, "分页查询不应该返回空")
        assertTrue(page1.size <= 3, "每页应该最多3条数据")

        val page2 = bookService.pageBooks(2, 3)
        assertNotNull(page2, "分页查询不应该返回空")

        println("分页查询成功, 第1页: ${page1.size} 条, 第2页: ${page2.size} 条")
    }

    @Test
    @Order(7)
    @DisplayName("测试删除图书")
    fun testDeleteBook() {
        assertTrue(testId != 0L, "图书ID不应该为空")

        val result = bookService.deleteBook(testId)
        assertTrue(result, "删除图书应该成功")

        val deleted = bookService.getBookById(testId)
        assertNull(deleted, "删除后查询应该返回空")
        println("删除图书成功, ID: $testId")
    }
}

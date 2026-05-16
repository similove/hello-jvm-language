package com.zjw.entity

/**
 * 图书表 (MySQL)
 */
class Book {
    var id: Long = 0
    var name: String? = null

    @JvmField
    var desc: String? = null
}

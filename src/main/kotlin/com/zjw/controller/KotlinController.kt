package com.zjw.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Kotlin 控制器
 */
@RestController
@RequestMapping("/kotlin")
class KotlinController {
    private val log = LoggerFactory.getLogger(KotlinController::class.java)

    @GetMapping("/hello")
    fun hello(): String {
        val startTime = System.currentTimeMillis()
        log.info("hello 入参: 无")
        val result = "Hello from Kotlin!"
        val costTime = System.currentTimeMillis() - startTime
        log.info("hello 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }

    @GetMapping("/info")
    fun info(): String {
        val startTime = System.currentTimeMillis()
        log.info("info 入参: 无")
        val result = "Kotlin version: 2.2.20"
        val costTime = System.currentTimeMillis() - startTime
        log.info("info 返回: result=$result, 耗时: ${costTime}ms")
        return result
    }
}

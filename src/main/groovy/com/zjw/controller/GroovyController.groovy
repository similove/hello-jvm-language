package com.zjw.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Groovy 控制器
 */
@RestController
@RequestMapping("/groovy")
class GroovyController {

    private static final Logger log = LoggerFactory.getLogger(GroovyController.class)

    @GetMapping("/hello")
    def hello() {
        log.info("hello 入参: 无")
        long startTime = System.currentTimeMillis()
        def result = "Hello from Groovy!"
        long costTime = System.currentTimeMillis() - startTime
        log.info("hello 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @GetMapping("/info")
    def info() {
        log.info("info 入参: 无")
        long startTime = System.currentTimeMillis()
        def result = "Groovy version: 5.0.6"
        long costTime = System.currentTimeMillis() - startTime
        log.info("info 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }
}

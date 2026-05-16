package com.zjw.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping, RestController}

/**
 * Scala 3 控制器
 */
@RestController
@RequestMapping(value = Array("/scala"))
class ScalaController {

  private val log = LoggerFactory.getLogger(classOf[ScalaController])

  @GetMapping(value = Array("/hello"))
  def hello: String = {
    val startTime = System.currentTimeMillis()
    log.info(s"hello 入参: 无")
    val result = "Hello from Scala 3!"
    val costTime = System.currentTimeMillis() - startTime
    log.info(s"hello 返回: result=${result}, 耗时: ${costTime}ms")
    result
  }

  @GetMapping(value = Array("/info"))
  def info: String = {
    val startTime = System.currentTimeMillis()
    log.info(s"info 入参: 无")
    val result = "Scala version: 3.8.0"
    val costTime = System.currentTimeMillis() - startTime
    log.info(s"info 返回: result=${result}, 耗时: ${costTime}ms")
    result
  }
}
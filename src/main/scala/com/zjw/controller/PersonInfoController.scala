package com.zjw.controller

import com.zjw.entity.PersonInfo
import com.zjw.service.PersonInfoService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation._

import java.util.HashMap
import java.util.List
import java.util.Map

/**
 * 人员信息 Controller (Scala)
 */
@RestController
@RequestMapping(value = Array("/person-info"))
class PersonInfoController(
    @org.springframework.beans.factory.annotation.Autowired
    private val personInfoService: PersonInfoService
) {

    private val log = LoggerFactory.getLogger(classOf[PersonInfoController])

    /**
     * 查询所有人员
     */
    @GetMapping(value = Array("/list"))
    def list(): List[PersonInfo] = {
        val startTime = System.currentTimeMillis()
        log.info(s"list 入参: 无")
        val result = personInfoService.listPersons()
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"list 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    /**
     * 分页查询
     * @param page 页码 (默认1)
     * @param pageSize 每页数量 (默认10)
     */
    @GetMapping(value = Array("/page"))
    def page(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): Map[String, Any] = {
        val startTime = System.currentTimeMillis()
        log.info(s"page 入参: page=${page}, pageSize=${pageSize}")
        val list = personInfoService.pagePersons(page, pageSize)
        val total = personInfoService.getTotalCount()
        val result = new HashMap[String, Any]()
        result.put("list", list)
        result.put("total", total)
        result.put("page", page)
        result.put("pageSize", pageSize)
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"page 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    /**
     * 根据ID查询
     */
    @GetMapping(value = Array("/{id}"))
    def getById(@PathVariable id: Int): PersonInfo = {
        val startTime = System.currentTimeMillis()
        log.info(s"getById 入参: id=${id}")
        val result = personInfoService.getPersonById(id)
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"getById 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    /**
     * 根据姓名查询
     */
    @GetMapping(value = Array("/name/{name}"))
    def getByName(@PathVariable name: String): PersonInfo = {
        val startTime = System.currentTimeMillis()
        log.info(s"getByName 入参: name=${name}")
        val result = personInfoService.getPersonByName(name)
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"getByName 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    /**
     * 新增人员
     */
    @PostMapping
    def save(@RequestBody person: PersonInfo): Map[String, Any] = {
        val startTime = System.currentTimeMillis()
        log.info(s"save 入参: person=${person}")
        val success = personInfoService.savePerson(person)
        val result = new HashMap[String, Any]()
        result.put("success", success)
        result.put("message", if (success) "新增成功" else "新增失败")
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"save 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    /**
     * 更新人员
     */
    @PutMapping(value = Array("/{id}"))
    def update(
        @PathVariable id: Int,
        @RequestBody person: PersonInfo
    ): Map[String, Any] = {
        val startTime = System.currentTimeMillis()
        log.info(s"update 入参: id=${id}, person=${person}")
        person.setId(id)
        val success = personInfoService.updatePerson(person)
        val result = new HashMap[String, Any]()
        result.put("success", success)
        result.put("message", if (success) "更新成功" else "更新失败")
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"update 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    /**
     * 删除人员
     */
    @DeleteMapping(value = Array("/{id}"))
    def delete(@PathVariable id: Int): Map[String, Any] = {
        val startTime = System.currentTimeMillis()
        log.info(s"delete 入参: id=${id}")
        val success = personInfoService.deletePerson(id)
        val result = new HashMap[String, Any]()
        result.put("success", success)
        result.put("message", if (success) "删除成功" else "删除失败")
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"delete 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }
}
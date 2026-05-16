package com.zjw.service.impl

import com.zjw.entity.PersonInfo
import com.zjw.mapper.PersonInfoMapper
import com.zjw.service.PersonInfoService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import java.util.List

/**
 * 人员信息服务实现 (PostgreSQL 从库)
 */
@Service
class PersonInfoServiceImpl(
    private val personInfoMapper: PersonInfoMapper
) extends PersonInfoService {

    private val log = LoggerFactory.getLogger(classOf[PersonInfoServiceImpl])

    override def listPersons(): List[PersonInfo] = {
        val startTime = System.currentTimeMillis()
        log.info(s"listPersons 入参: 无")
        val result = personInfoMapper.selectList()
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"listPersons 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def pagePersons(page: Int, pageSize: Int): List[PersonInfo] = {
        val startTime = System.currentTimeMillis()
        log.info(s"pagePersons 入参: page=${page}, pageSize=${pageSize}")
        val offset = (page - 1) * pageSize
        val result = personInfoMapper.selectPage(pageSize, offset)
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"pagePersons 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def getPersonById(id: Int): PersonInfo = {
        val startTime = System.currentTimeMillis()
        log.info(s"getPersonById 入参: id=${id}")
        val result = personInfoMapper.selectById(id)
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"getPersonById 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def getPersonByName(name: String): PersonInfo = {
        val startTime = System.currentTimeMillis()
        log.info(s"getPersonByName 入参: name=${name}")
        val result = personInfoMapper.selectByName(name)
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"getPersonByName 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def savePerson(person: PersonInfo): Boolean = {
        val startTime = System.currentTimeMillis()
        log.info(s"savePerson 入参: person=${person}")
        val result = personInfoMapper.insert(person) > 0
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"savePerson 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def updatePerson(person: PersonInfo): Boolean = {
        val startTime = System.currentTimeMillis()
        log.info(s"updatePerson 入参: person=${person}")
        val result = personInfoMapper.update(person) > 0
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"updatePerson 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def deletePerson(id: Int): Boolean = {
        val startTime = System.currentTimeMillis()
        log.info(s"deletePerson 入参: id=${id}")
        val result = personInfoMapper.delete(id) > 0
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"deletePerson 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }

    override def getTotalCount(): Long = {
        val startTime = System.currentTimeMillis()
        log.info(s"getTotalCount 入参: 无")
        val result = personInfoMapper.count()
        val costTime = System.currentTimeMillis() - startTime
        log.info(s"getTotalCount 返回: result=${result}, 耗时: ${costTime}ms")
        result
    }
}
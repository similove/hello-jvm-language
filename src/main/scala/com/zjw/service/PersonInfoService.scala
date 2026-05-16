package com.zjw.service

import com.zjw.entity.PersonInfo

import java.util.List

/**
 * 人员信息服务接口
 */
trait PersonInfoService {

    /**
     * 查询所有人员
     */
    def listPersons(): List[PersonInfo]

    /**
     * 分页查询
     * @param page 页码 (从1开始)
     * @param pageSize 每页数量
     * @return 人员列表
     */
    def pagePersons(page: Int, pageSize: Int): List[PersonInfo]

    /**
     * 根据ID查询
     */
    def getPersonById(id: Int): PersonInfo

    /**
     * 根据姓名查询
     */
    def getPersonByName(name: String): PersonInfo

    /**
     * 新增人员
     */
    def savePerson(person: PersonInfo): Boolean

    /**
     * 更新人员
     */
    def updatePerson(person: PersonInfo): Boolean

    /**
     * 删除人员
     */
    def deletePerson(id: Int): Boolean

    /**
     * 查询总数
     */
    def getTotalCount(): Long
}

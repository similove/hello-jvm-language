package com.zjw

import com.zjw.entity.PersonInfo
import com.zjw.mapper.PersonInfoMapper
import com.zjw.service.PersonInfoService
import org.junit.jupiter.api._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.util
import scala.collection.mutable

/**
 * PersonInfo 单元测试 (Scala)
 */
@SpringBootTest
@TestMethodOrder(classOf[org.junit.jupiter.api.MethodOrderer$OrderAnnotation])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonInfoServiceTest {

  @Autowired
  private val personInfoService: PersonInfoService = null

  @Autowired
  private val personInfoMapper: PersonInfoMapper = null

  private var testId: Int = 0
  private val testName: String = "test_person_scala_" + System.currentTimeMillis()

  @Test
  @Order(1)
  @DisplayName("测试新增人员")
  def testSavePerson(): Unit = {
    val person = new PersonInfo()
    person.setId(testId)
    person.setName(testName)
    person.setAge(28)

    val result = personInfoService.savePerson(person)
    assert(result, "新增人员应该成功")

    testId = person.getId
    assert(testId != 0, "人员ID不应该为空")
    println(s"新增人员成功, ID: $testId")
  }

  @Test
  @Order(2)
  @DisplayName("测试根据ID查询人员")
  def testGetPersonById(): Unit = {
    assert(testId != 0, "人员ID不应该为空")

    val person = personInfoService.getPersonById(testId)
    assert(person != null, "根据ID查询人员不应该为空")
    assert(testName == person.getName, "姓名应该匹配")
    println(s"根据ID查询人员成功: $person")
  }

  @Test
  @Order(3)
  @DisplayName("测试根据姓名查询人员")
  def testGetPersonByName(): Unit = {
    val person = personInfoService.getPersonByName(testName)
    assert(person != null, "根据姓名查询人员不应该为空")
    assert(testName == person.getName, "姓名应该匹配")
    println(s"根据姓名查询人员成功: $person")
  }

  @Test
  @Order(4)
  @DisplayName("测试更新人员")
  def testUpdatePerson(): Unit = {
    assert(testId != 0, "人员ID不应该为空")

    val person = personInfoService.getPersonById(testId)
    person.setAge(35)

    val result = personInfoService.updatePerson(person)
    assert(result, "更新人员应该成功")

    val updated = personInfoService.getPersonById(testId)
    assert(35 == updated.getAge, "年龄应该更新为35")
    println(s"更新人员成功: $updated")
  }

  @Test
  @Order(5)
  @DisplayName("测试查询所有人员")
  def testListPersons(): Unit = {
    val persons: util.List[PersonInfo] = personInfoService.listPersons()
    assert(persons != null, "人员列表不应该为空")
    assert(persons.size() > 0, "人员列表应该包含数据")
    println(s"查询所有人员成功, 共 ${persons.size()} 条")
  }

  @Test
  @Order(6)
  @DisplayName("测试分页查询人员")
  def testPagePersons(): Unit = {
    val page1: util.List[PersonInfo] = personInfoService.pagePersons(1, 3)
    assert(page1 != null, "分页查询不应该返回空")
    assert(page1.size() <= 3, "每页应该最多3条数据")

    val page2: util.List[PersonInfo] = personInfoService.pagePersons(2, 3)
    assert(page2 != null, "分页查询不应该返回空")

    println(s"分页查询成功, 第1页: ${page1.size()} 条, 第2页: ${page2.size()} 条")
  }

  @Test
  @Order(7)
  @DisplayName("测试删除人员")
  def testDeletePerson(): Unit = {
    assert(testId != 0, "人员ID不应该为空")

    val result = personInfoService.deletePerson(testId)
    assert(result, "删除人员应该成功")

    val deleted = personInfoService.getPersonById(testId)
    assert(deleted == null, "删除后查询应该返回空")
    println(s"删除人员成功, ID: $testId")
  }
}

package com.zjw.entity

/**
 * 人员信息表 (PostgreSQL)
 */
class PersonInfo {
  var id: Int = _
  var name: String = _
  var age: Int = _

  def getId: Int = id
  def setId(id: Int): Unit = this.id = id

  def getName: String = name
  def setName(name: String): Unit = this.name = name

  def getAge: Int = age
  def setAge(age: Int): Unit = this.age = age

  override def toString: String = s"PersonInfo(id=$id, name=$name, age=$age)"
}

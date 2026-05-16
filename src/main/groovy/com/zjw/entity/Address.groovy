package com.zjw.entity

/**
 * 地址表 (PostgreSQL)
 */
class Address {
    private Integer id
    private String name
    private String detail
    private String desc

    Integer getId() { return id }
    void setId(Integer id) { this.id = id }

    String getName() { return name }
    void setName(String name) { this.name = name }

    String getDetail() { return detail }
    void setDetail(String detail) { this.detail = detail }

    String getDesc() { return desc }
    void setDesc(String desc) { this.desc = desc }

    @Override
    String toString() {
        return "Address(id=$id, name=$name, detail=$detail, desc=$desc)"
    }
}

package com.zjw

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue

import com.zjw.entity.Address
import com.zjw.mapper.AddressMapper
import com.zjw.service.AddressService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Address 单元测试 (Groovy)
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressServiceTest {

    @Autowired
    AddressService addressService

    @Autowired
    AddressMapper addressMapper

    static int testId = 0
    static String testName = "test_address_groovy_" + System.currentTimeMillis()

    @Test
    @Order(1)
    @DisplayName("测试新增地址")
    void testSaveAddress() {
        def address = new Address()
        address.setId(testId)
        address.setName(testName)
        address.setDetail("测试详细地址")
        address.setDesc("测试描述")

        def result = addressService.saveAddress(address)
        assertTrue(result, "新增地址应该成功")

        testId = address.getId
        assertTrue(testId != 0, "地址ID不应该为空")
        println "新增地址成功, ID: ${testId}"
    }

    @Test
    @Order(2)
    @DisplayName("测试根据ID查询地址")
    void testGetAddressById() {
        assertTrue(testId != 0, "地址ID不应该为空")

        def address = addressService.getAddressById(testId)
        assertNotNull(address, "根据ID查询地址不应该为空")
        assertEquals(testName, address.getName(), "姓名应该匹配")
        println "根据ID查询地址成功: ${address}"
    }

    @Test
    @Order(3)
    @DisplayName("测试根据姓名查询地址")
    void testGetAddressByName() {
        def address = addressService.getAddressByName(testName)
        assertNotNull(address, "根据姓名查询地址不应该为空")
        assertEquals(testName, address.getName(), "姓名应该匹配")
        println "根据姓名查询地址成功: ${address}"
    }

    @Test
    @Order(4)
    @DisplayName("测试更新地址")
    void testUpdateAddress() {
        assertTrue(testId != 0, "地址ID不应该为空")

        def address = addressService.getAddressById(testId)
        address.setDetail("更新后的详细地址")

        def result = addressService.updateAddress(address)
        assertTrue(result, "更新地址应该成功")

        def updated = addressService.getAddressById(testId)
        assertEquals("更新后的详细地址", updated.getDetail(), "详细地址应该更新")
        println "更新地址成功: ${updated}"
    }

    @Test
    @Order(5)
    @DisplayName("测试查询所有地址")
    void testListAddresses() {
        def addresses = addressService.listAddresses()
        assertNotNull(addresses, "地址列表不应该为空")
        assertTrue(addresses.size() > 0, "地址列表应该包含数据")
        println "查询所有地址成功, 共 ${addresses.size()} 条"
    }

    @Test
    @Order(6)
    @DisplayName("测试分页查询地址")
    void testPageAddresses() {
        def page1 = addressService.pageAddresses(1, 3)
        assertNotNull(page1, "分页查询不应该返回空")
        assertTrue(page1.size() <= 3, "每页应该最多3条数据")

        def page2 = addressService.pageAddresses(2, 3)
        assertNotNull(page2, "分页查询不应该返回空")

        println "分页查询成功, 第1页: ${page1.size()} 条, 第2页: ${page2.size()} 条"
    }

    @Test
    @Order(7)
    @DisplayName("测试删除地址")
    void testDeleteAddress() {
        assertTrue(testId != 0, "地址ID不应该为空")

        def result = addressService.deleteAddress(testId)
        assertTrue(result, "删除地址应该成功")

        def deleted = addressService.getAddressById(testId)
        assertNull(deleted, "删除后查询应该返回空")
        println "删除地址成功, ID: ${testId}"
    }
}

package com.zjw.controller

import com.zjw.entity.Address
import com.zjw.service.AddressService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 地址控制器 (Groovy)
 */
@RestController
@RequestMapping(value = "/address")
class AddressController {

    private static final Logger log = LoggerFactory.getLogger(AddressController.class)

    private final AddressService addressService

    @Autowired
    AddressController(AddressService addressService) {
        this.addressService = addressService
    }

    /**
     * 查询所有地址
     */
    @GetMapping(value = "/list")
    List<Address> list() {
        log.info("list 入参: 无")
        long startTime = System.currentTimeMillis()
        List<Address> result = addressService.listAddresses()
        long costTime = System.currentTimeMillis() - startTime
        log.info("list 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    /**
     * 分页查询
     * @param page 页码 (默认1)
     * @param pageSize 每页数量 (默认10)
     */
    @GetMapping(value = "/page")
    Map<String, Object> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        log.info("page 入参: page={}, pageSize={}", page, pageSize)
        long startTime = System.currentTimeMillis()
        def list = addressService.pageAddresses(page, pageSize)
        def total = addressService.getTotalCount()
        def result = [
            list: list,
            total: total,
            page: page,
            pageSize: pageSize
        ]
        long costTime = System.currentTimeMillis() - startTime
        log.info("page 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    /**
     * 根据ID查询
     */
    @GetMapping(value = "/{id}")
    Address getById(@PathVariable int id) {
        log.info("getById 入参: id={}", id)
        long startTime = System.currentTimeMillis()
        Address result = addressService.getAddressById(id)
        long costTime = System.currentTimeMillis() - startTime
        log.info("getById 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    /**
     * 根据姓名查询
     */
    @GetMapping(value = "/name/{name}")
    Address getByName(@PathVariable String name) {
        log.info("getByName 入参: name={}", name)
        long startTime = System.currentTimeMillis()
        Address result = addressService.getAddressByName(name)
        long costTime = System.currentTimeMillis() - startTime
        log.info("getByName 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    /**
     * 新增地址
     */
    @PostMapping
    Map<String, Object> save(@RequestBody Address address) {
        log.info("save 入参: address={}", address)
        long startTime = System.currentTimeMillis()
        def success = addressService.saveAddress(address)
        def result = [
            success: success,
            message: success ? "新增成功" : "新增失败"
        ]
        long costTime = System.currentTimeMillis() - startTime
        log.info("save 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    /**
     * 更新地址
     */
    @PutMapping(value = "/{id}")
    Map<String, Object> update(@PathVariable int id, @RequestBody Address address) {
        log.info("update 入参: id={}, address={}", id, address)
        long startTime = System.currentTimeMillis()
        address.setId(id)
        def success = addressService.updateAddress(address)
        def result = [
            success: success,
            message: success ? "更新成功" : "更新失败"
        ]
        long costTime = System.currentTimeMillis() - startTime
        log.info("update 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    /**
     * 删除地址
     */
    @DeleteMapping(value = "/{id}")
    Map<String, Object> delete(@PathVariable int id) {
        log.info("delete 入参: id={}", id)
        long startTime = System.currentTimeMillis()
        def success = addressService.deleteAddress(id)
        def result = [
            success: success,
            message: success ? "删除成功" : "删除失败"
        ]
        long costTime = System.currentTimeMillis() - startTime
        log.info("delete 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }
}

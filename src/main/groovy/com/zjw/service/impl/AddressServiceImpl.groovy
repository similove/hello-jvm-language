package com.zjw.service.impl

import com.zjw.entity.Address
import com.zjw.mapper.AddressMapper
import com.zjw.service.AddressService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 地址服务实现 (PostgreSQL 从库)
 */
@Service
class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class)

    private final AddressMapper addressMapper

    AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper
    }

    @Override
    List<Address> listAddresses() {
        log.info("listAddresses 入参: 无")
        long startTime = System.currentTimeMillis()
        List<Address> result = addressMapper.selectList()
        long costTime = System.currentTimeMillis() - startTime
        log.info("listAddresses 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    List<Address> pageAddresses(int page, int pageSize) {
        log.info("pageAddresses 入参: page={}, pageSize={}", page, pageSize)
        long startTime = System.currentTimeMillis()
        int offset = (page - 1) * pageSize
        List<Address> result = addressMapper.selectPage(pageSize, offset)
        long costTime = System.currentTimeMillis() - startTime
        log.info("pageAddresses 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    Address getAddressById(int id) {
        log.info("getAddressById 入参: id={}", id)
        long startTime = System.currentTimeMillis()
        Address result = addressMapper.selectById(id)
        long costTime = System.currentTimeMillis() - startTime
        log.info("getAddressById 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    Address getAddressByName(String name) {
        log.info("getAddressByName 入参: name={}", name)
        long startTime = System.currentTimeMillis()
        Address result = addressMapper.selectByName(name)
        long costTime = System.currentTimeMillis() - startTime
        log.info("getAddressByName 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    boolean saveAddress(Address address) {
        log.info("saveAddress 入参: address={}", address)
        long startTime = System.currentTimeMillis()
        boolean result = addressMapper.insert(address) > 0
        long costTime = System.currentTimeMillis() - startTime
        log.info("saveAddress 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    boolean updateAddress(Address address) {
        log.info("updateAddress 入参: address={}", address)
        long startTime = System.currentTimeMillis()
        boolean result = addressMapper.update(address) > 0
        long costTime = System.currentTimeMillis() - startTime
        log.info("updateAddress 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    boolean deleteAddress(int id) {
        log.info("deleteAddress 入参: id={}", id)
        long startTime = System.currentTimeMillis()
        boolean result = addressMapper.delete(id) > 0
        long costTime = System.currentTimeMillis() - startTime
        log.info("deleteAddress 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }

    @Override
    long getTotalCount() {
        log.info("getTotalCount 入参: 无")
        long startTime = System.currentTimeMillis()
        long result = addressMapper.count()
        long costTime = System.currentTimeMillis() - startTime
        log.info("getTotalCount 返回: result={}, 耗时: {}ms", result, costTime)
        return result
    }
}

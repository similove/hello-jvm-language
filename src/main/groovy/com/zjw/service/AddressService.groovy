package com.zjw.service

import com.zjw.entity.Address

/**
 * 地址服务接口
 */
interface AddressService {

    /**
     * 查询所有地址
     */
    List<Address> listAddresses()

    /**
     * 分页查询
     * @param page 页码 (从1开始)
     * @param pageSize 每页数量
     * @return 地址列表
     */
    List<Address> pageAddresses(int page, int pageSize)

    /**
     * 根据ID查询
     */
    Address getAddressById(int id)

    /**
     * 根据姓名查询
     */
    Address getAddressByName(String name)

    /**
     * 新增地址
     */
    boolean saveAddress(Address address)

    /**
     * 更新地址
     */
    boolean updateAddress(Address address)

    /**
     * 删除地址
     */
    boolean deleteAddress(int id)

    /**
     * 查询总数
     */
    long getTotalCount()
}

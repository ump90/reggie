package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.AddressBook;

/**
 * @author UMP90
 * @date 2021/10/17
 */

public interface AddressBookService extends IService<AddressBook> {
    CommonReturn<?> addAddressBook(AddressBook addressBook);

    CommonReturn<?> listAddressBook();

    CommonReturn<?> getDefaultAddress();

    CommonReturn<?> setDefaultAddress(Long id);

    CommonReturn<?> getAddressById(Long id);

    CommonReturn<?> updateAddress(AddressBook addressBook);
}


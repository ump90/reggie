package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.BaseContext;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.AddressBook;
import com.itheima.reggie_take_out.mapper.AddressBookMapper;
import com.itheima.reggie_take_out.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    @Override
    public CommonReturn<?> addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getId());
        this.save(addressBook);
        return CommonReturn.success("地址保存成功");
    }

    public CommonReturn<?> listAddressBook() {
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long id = BaseContext.getId();
        lambdaQueryWrapper.eq(id != null, AddressBook::getUserId, id).orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBookList = this.list(lambdaQueryWrapper);
        return CommonReturn.success(addressBookList);
    }

    @Override
    public CommonReturn<?> getDefaultAddress() {
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(userId != null, AddressBook::getUserId, userId).eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = this.getOne(lambdaQueryWrapper);
        return CommonReturn.success(addressBook);
    }

    @Override
    @Transactional
    public CommonReturn<?> setDefaultAddress(Long id) {
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(userId != null, AddressBook::getUserId, userId);
        List<AddressBook> list = this.list(lambdaQueryWrapper);
        list.forEach(addressBook -> {
            if (Objects.equals(id, addressBook.getId())) {
                addressBook.setIsDefault(1);
            } else {
                addressBook.setIsDefault(0);
            }
        });
        this.updateBatchById(list);
        return CommonReturn.success("设置默认地址成功");

    }

    @Override
    public CommonReturn<?> getAddressById(Long id) {

        AddressBook addressBook = this.getById(id);
        return CommonReturn.success(addressBook);
    }

    @Override
    public CommonReturn<?> updateAddress(AddressBook addressBook) {
        this.updateById(addressBook);
        return CommonReturn.success("更新地址成功");
    }
}

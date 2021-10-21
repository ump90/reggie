package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.AddressBook;
import com.itheima.reggie_take_out.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public CommonReturn<?> add(@RequestBody AddressBook addressBook) {
        return addressBookService.addAddressBook(addressBook);
    }

    @GetMapping("/list")
    public CommonReturn<?> list() {
        return addressBookService.listAddressBook();
    }

    @GetMapping("/default")
    public CommonReturn<?> getDefault() {
        return addressBookService.getDefaultAddress();
    }

    @PutMapping("/default")
    public CommonReturn<?> setDefault(@RequestBody Map<String, Long> map) {
        Long id = map.get("id");
        return addressBookService.setDefaultAddress(id);

    }

    @GetMapping("/{id}")
    public CommonReturn<?> getById(@PathVariable Long id) {
        return addressBookService.getAddressById(id);
    }

    @PutMapping
    public CommonReturn<?> updateAddress(@RequestBody AddressBook addressBook) {
        return addressBookService.updateAddress(addressBook);
    }

}

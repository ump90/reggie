package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.ShoppingCart;
import com.itheima.reggie_take_out.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public CommonReturn<?> add(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.addIntoShoppingCart(shoppingCart);
    }

    @GetMapping("/list")
    public CommonReturn<?> get() {
        return shoppingCartService.listShoppingCart();
    }

    @DeleteMapping("/clean")
    public CommonReturn<?> clean() {
        return shoppingCartService.cleanShoppingCart();
    }

}

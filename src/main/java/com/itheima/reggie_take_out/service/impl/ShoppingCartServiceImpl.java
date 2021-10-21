package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.BaseContext;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.ShoppingCart;
import com.itheima.reggie_take_out.mapper.ShoppingCartMapper;
import com.itheima.reggie_take_out.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {


    @Override
    public CommonReturn<?> addIntoShoppingCart(ShoppingCart shoppingCart) {
        Long userId = BaseContext.getId();
        shoppingCart.setUserId(userId);
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dishId != null, ShoppingCart::getDishId, dishId);
        lambdaQueryWrapper.eq(setmealId != null, ShoppingCart::getSetmealId, setmealId);
        long number = this.count(lambdaQueryWrapper);
        if (number > 0) {
            ShoppingCart savedShoppingCart = this.getById(shoppingCart.getId());
            number++;
            savedShoppingCart.setNumber((int) number);
            this.updateById(savedShoppingCart);
        } else {
            shoppingCart.setNumber(1);
            this.save(shoppingCart);
        }
        return CommonReturn.success("更新购物车成功");

    }

    @Override
    public CommonReturn<?> listShoppingCart() {
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = this.list(lambdaQueryWrapper);
        return CommonReturn.success(shoppingCarts);

    }

    @Override
    public CommonReturn<?> cleanShoppingCart() {
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
        if (this.remove(lambdaQueryWrapper)) {
            return CommonReturn.success("清空购物车成功");
        } else {
            return CommonReturn.error("清空购物车失败");
        }
    }
}

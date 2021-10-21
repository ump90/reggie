package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.BaseContext;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.*;
import com.itheima.reggie_take_out.mapper.OrderMapper;
import com.itheima.reggie_take_out.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Transactional
    @Override
    public CommonReturn<?> submitOrder(String remark, Integer payMethod, Long addressBookId) {
        Long userId = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        User user = userService.getById(userId);
        LambdaQueryWrapper<AddressBook> addressBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressBookLambdaQueryWrapper.eq(userId != null, AddressBook::getUserId, userId);
        addressBookLambdaQueryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(addressBookLambdaQueryWrapper);

        if (shoppingCartList == null) {
            return CommonReturn.error("购物车空空的，就像我的钱包");
        }
        if (addressBook == null) {
            return CommonReturn.error("没写送货地址");
        }

        Long orderId = IdWorker.getId();
        AtomicInteger atomicInteger = new AtomicInteger(0);

        List<OrderDetail> orderDetails = new ArrayList<>();
        final BigDecimal[] totalMoney = {BigDecimal.ZERO};

        shoppingCartList.forEach(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setOrderId(orderId);
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setAmount(shoppingCart.getAmount());
            orderDetails.add(orderDetail);
            atomicInteger.incrementAndGet();

            BigDecimal price = BigDecimal.valueOf(shoppingCart.getAmount());
            BigDecimal number = BigDecimal.valueOf(shoppingCart.getNumber());
            totalMoney[0] = totalMoney[0].add(price.multiply(number));
        });

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        Double orderAmount = Double.parseDouble(numberFormat.format(totalMoney[0]));
        Order order = new Order();
        StringBuilder stringBuilder = new StringBuilder();
        String provinceName = addressBook.getProvinceName();
        String cityName = addressBook.getCityName();
        String districtName = addressBook.getDistrictName();
        String detail = addressBook.getDetail();
        if (!StringUtils.isBlank(provinceName)) {
            stringBuilder.append(provinceName);
        }
        if (!StringUtils.isBlank(cityName)) {
            stringBuilder.append(cityName);
        }

        if (!StringUtils.isBlank(districtName)) {
            stringBuilder.append(districtName);
        }
        if ((!StringUtils.isBlank(detail))) {
            stringBuilder.append(detail);
        }

        order.setAddress(stringBuilder.toString());
        order.setAddressBookId(addressBook.getId());
        order.setAmount(orderAmount);
        order.setConsignee(addressBook.getConsignee());
        order.setId(orderId);
        order.setNumber(orderId.toString());
        order.setPhone(user.getPhone());
        order.setRemark(remark);
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(LocalDateTime.now());
        order.setStatus(2);
        order.setUserId(userId);
        order.setUserName(user.getName());
        order.setPayMethod(payMethod);
        this.save(order);
        orderDetailService.saveBatch(orderDetails);

        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);


        return CommonReturn.success("下单成功");

    }

    @Override
    public CommonReturn<?> pageOrder(Integer page, Integer pageSize, LocalDateTime beginTime, LocalDateTime endTime, String number) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.between((beginTime != null && endTime != null), Order::getCheckoutTime, beginTime, endTime);
        lambdaQueryWrapper.like(number != null, Order::getNumber, number);
        this.page(orderPage, lambdaQueryWrapper);
        return CommonReturn.success(orderPage);
    }

    @Override
    public CommonReturn<?> updateStatus(Long id, Integer status) {
        Order order = this.getById(id);
        order.setStatus(status);
        this.updateById(order);
        return CommonReturn.success("更新状态成功");
    }
}

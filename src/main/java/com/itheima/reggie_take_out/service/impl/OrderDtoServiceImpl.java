package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.dto.OrderDto;
import com.itheima.reggie_take_out.entity.Order;
import com.itheima.reggie_take_out.entity.OrderDetail;
import com.itheima.reggie_take_out.service.OrderDetailService;
import com.itheima.reggie_take_out.service.OrderDtoService;
import com.itheima.reggie_take_out.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/18
 */
@Service
public class OrderDtoServiceImpl implements OrderDtoService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public CommonReturn<?> userPage(Integer page, Integer pageSize) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Order::getCheckoutTime);
        orderService.page(orderPage, lambdaQueryWrapper);
        List<Order> orderList = orderPage.getRecords();
        List<OrderDto> orderDtos = new ArrayList<>();
        orderList.forEach(order -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(OrderDetail::getOrderId, order.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(lambdaQueryWrapper1);
            orderDto.setOrderDetails(orderDetails);
            int sumNum = 0;
            for (OrderDetail detail : orderDetails) {
                sumNum = sumNum + detail.getNumber();
            }
            orderDto.setSumNum(sumNum);

            orderDtos.add(orderDto);
        });
        Page<OrderDto> orderDtoPage = new Page<>();
        BeanUtils.copyProperties(orderPage, orderDtoPage, "records");
        orderDtoPage.setRecords(orderDtos);

        return CommonReturn.success(orderDtoPage);
    }


}

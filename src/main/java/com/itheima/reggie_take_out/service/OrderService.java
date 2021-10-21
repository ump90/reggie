package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Order;

import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/17
 */

public interface OrderService extends IService<Order> {
    CommonReturn<?> submitOrder(String remark, Integer payMethod, Long addressBookId);

    CommonReturn<?> pageOrder(Integer page, Integer pageSize, LocalDateTime beginTime, LocalDateTime endTime, String number);

    CommonReturn<?> updateStatus(Long id, Integer status);
}

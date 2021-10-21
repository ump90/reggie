package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.entity.OrderDetail;
import com.itheima.reggie_take_out.mapper.OrderDetailMapper;
import com.itheima.reggie_take_out.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}

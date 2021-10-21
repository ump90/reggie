package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.entity.Dish;
import com.itheima.reggie_take_out.mapper.DishMapper;
import com.itheima.reggie_take_out.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @author UMP90
 * @date 2021/10/15
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}

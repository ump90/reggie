package com.itheima.reggie_take_out.service;

import com.itheima.reggie_take_out.common.CommonReturn;

/**
 * @author UMP90
 * @date 2021/10/18
 */

public interface OrderDtoService {
    CommonReturn<?> userPage(Integer page, Integer pageSize);
}

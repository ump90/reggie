package com.itheima.reggie_take_out.service;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.dto.SetmealDto;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/16
 */

public interface SetmealDtoService {
    CommonReturn<?> saveWithDish(SetmealDto setmealDto);

    CommonReturn<?> page(Integer page, Integer pageSize, String name);

    CommonReturn<?> deleteByIds(List<Long> ids);

    CommonReturn<?> updateStatus(List<Long> ids, Integer status);

    CommonReturn<?> getByCategoryId(Long categoryId, Integer status);

    CommonReturn<?> getSetmealById(Long id);

    CommonReturn<?> updateSetmeal(SetmealDto setmealDto);
}

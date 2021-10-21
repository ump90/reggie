package com.itheima.reggie_take_out.service;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.dto.DishDto;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/15
 */

public interface DishDtoService {
    CommonReturn<?> save(DishDto dishDto);

    CommonReturn<?> saveWithFlavor(DishDto dishDto);

    CommonReturn<?> list(Integer page, Integer pageSize, String name);

    CommonReturn<?> getByIdWithFlavor(Long id);

    CommonReturn<?> updateWithFlavors(DishDto dishDto);

    CommonReturn<?> getByCategoryId(Long categoryId, Integer status);

    CommonReturn<?> deleteDish(List<Long> ids);

    CommonReturn<?> setDishStatus(List<Long> ids, Integer status);
}

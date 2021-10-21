package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Category;

/**
 * @author UMP90
 * @date 2021/10/14
 */

public interface CategoryService extends IService<Category> {
    CommonReturn<?> getPage(int page, int pageSize);

    CommonReturn<?> updateCategory(Category category);

    CommonReturn<?> deleteCategory(Long id);

    CommonReturn<?> addCategory(Category category);

    CommonReturn<?> list(Long id);
}

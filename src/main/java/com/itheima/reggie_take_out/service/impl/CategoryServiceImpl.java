package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Category;
import com.itheima.reggie_take_out.mapper.CategoryMapper;
import com.itheima.reggie_take_out.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/14
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public CommonReturn<?> getPage(int page, int pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        this.page(categoryPage);
        return CommonReturn.success(categoryPage);
    }

    @Override
    public CommonReturn<?> addCategory(Category category) {


        if (this.save(category)) {
            return CommonReturn.success("新增分类成功");
        } else {
            return CommonReturn.error("新增分类失败");
        }
    }

    @Override
    public CommonReturn<?> deleteCategory(Long id) {


        if (this.removeById(id)) {
            return CommonReturn.success("删除成功");
        } else {
            return CommonReturn.error("删除失败 分类不存在");
        }
    }

    @Override
    public CommonReturn<?> updateCategory(Category category) {
        if (this.updateById(category)) {
            return CommonReturn.success("更新分类成功");
        } else {
            return CommonReturn.error("更新分类失败");
        }
    }

    @Override
    public CommonReturn<?> list(Long type) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(type != null, Category::getType, type);
        List<Category> categoryList = this.list(lambdaQueryWrapper);
        return CommonReturn.success(categoryList);

    }
}

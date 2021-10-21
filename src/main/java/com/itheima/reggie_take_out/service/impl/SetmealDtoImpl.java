package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.dto.SetmealDto;
import com.itheima.reggie_take_out.entity.Setmeal;
import com.itheima.reggie_take_out.entity.SetmealDish;
import com.itheima.reggie_take_out.service.CategoryService;
import com.itheima.reggie_take_out.service.SetmealDishService;
import com.itheima.reggie_take_out.service.SetmealDtoService;
import com.itheima.reggie_take_out.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/16
 */
@Service
public class SetmealDtoImpl implements SetmealDtoService {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public CommonReturn<?> saveWithDish(SetmealDto setmealDto) {
        setmealService.save(setmealDto);
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        setmealDishService.saveBatch(setmealDishList);
        return CommonReturn.success("套餐保存成功");
    }

    @Override
    @Transactional
    public CommonReturn<?> page(Integer page, Integer pageSize, String name) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage, lambdaQueryWrapper);
        List<Setmeal> setmealList = setmealPage.getRecords();
        List<SetmealDto> setmealDtos = new ArrayList<>();
        setmealList.forEach(setmeal -> {
            Long categoryId = setmeal.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setCategoryName(categoryName);
            setmealDtos.add(setmealDto);
        });
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        setmealDtoPage.setRecords(setmealDtos);
        return CommonReturn.success(setmealDtoPage);
    }

    @Override
    @Transactional
    public CommonReturn<?> deleteByIds(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        if (setmealService.count(lambdaQueryWrapper) > 0) {
            return CommonReturn.error("有套餐未被禁用");
        } else {
            setmealService.removeByIds(ids);
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.in(SetmealDish::getDishId, ids);
            setmealDishService.remove(setmealDishLambdaQueryWrapper);
            return CommonReturn.success("套餐删除成功");
        }

    }

    @Override
    public CommonReturn<?> updateStatus(List<Long> ids, Integer status) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> setmealList = setmealService.list(lambdaQueryWrapper);
        setmealList.forEach(
                setmeal -> {
                    setmeal.setStatus(status);
                }
        );
        setmealService.updateBatchById(setmealList);
        return CommonReturn.success("更新状态成功");
    }

    @Override
    public CommonReturn<?> getByCategoryId(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(categoryId != null, Setmeal::getCategoryId, categoryId)
                .eq(status != null, Setmeal::getStatus, status);
        List<Setmeal> setmealList = setmealService.list(lambdaQueryWrapper);
        return CommonReturn.success(setmealList);
    }

    @Override
    public CommonReturn<?> getSetmealById(Long id) {
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(list);
        return CommonReturn.success(setmealDto);
    }

    @Override
    @Transactional
    public CommonReturn<?> updateSetmeal(SetmealDto setmealDto) {
        setmealService.updateById(setmealDto);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList.forEach(setmealDish -> setmealDish.setSetmealId(setmealDto.getId()));
        setmealDishService.saveBatch(setmealDishList);
        return CommonReturn.success("更新成功");
    }
}


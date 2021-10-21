package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.*;
import com.itheima.reggie_take_out.dto.DishDto;
import com.itheima.reggie_take_out.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author UMP90
 * @date 2021/10/15
 */
@Service
@Slf4j
public class DishDtoServiceImpl implements DishDtoService {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public CommonReturn<?> save(DishDto dishDto) {
        dishService.save(dishDto);
        return CommonReturn.success("Add dish success");
    }

    @Override
    @Transactional
    public CommonReturn<?> saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors.forEach(s -> {
            s.setDishId(id);
        });
        String key = "dish_" + dishDto.getId() + "_" + dishDto.getStatus();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
        if (dishFlavorService.saveBatch(dishFlavors)) {
            return CommonReturn.success("Add dish with flavors success");
        } else {
            return CommonReturn.error("Fail to add dish with flavors");
        }


    }

    @Override
    @Transactional
    public CommonReturn<?> list(Integer page, Integer pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasLength(name), Dish::getName, name).orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage, lambdaQueryWrapper);
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        List<Dish> list = dishPage.getRecords();

        List<DishDto> dishDtoList = new ArrayList<>();

        for (Dish dish : list) {
            String key = "dish_" + dish.getId() + "_" + dish.getStatus();
            boolean is_Cache = Boolean.TRUE.equals(redisTemplate.hasKey(key));
            DishDto dishDto = new DishDto();
            if (is_Cache) {
                dishDto = (DishDto) redisTemplate.opsForValue().get(key);
                dishDtoList.add(dishDto);
            } else {
                BeanUtils.copyProperties(dish, dishDto);
                Long categoryId = dishService.getById(dish.getId()).getCategoryId();
                String categoryName = categoryService.getById(categoryId).getName();
                if (categoryName != null) {
                    dishDto.setCategoryName(categoryName);
                }
                dishDtoList.add(dishDto);
                redisTemplate.opsForValue().set(key, dishDto, 300, TimeUnit.SECONDS);
            }
        }

        dishDtoPage.setRecords(dishDtoList);
        return CommonReturn.success(dishDtoPage);

    }

    @Override
    public CommonReturn<?> getByIdWithFlavor(Long id) {
        Dish dish = dishService.getById(id);
        String key = "dish_" + dish.getId() + "_" + dish.getStatus();
        boolean is_Cache = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        DishDto dishDto = new DishDto();
        if (is_Cache) {
            dishDto = (DishDto) redisTemplate.opsForValue().get(key);
        } else {
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
            List<DishFlavor> flavors = dishFlavorService.list(lambdaQueryWrapper);
            BeanUtils.copyProperties(dish, dishDto);
            dishDto.setFlavors(flavors);
            redisTemplate.opsForValue().set(key, dishDto, 300, TimeUnit.SECONDS);
        }
        return CommonReturn.success(dishDto);
    }

    @Transactional
    @Override
    public CommonReturn<?> updateWithFlavors(DishDto dishDto) {
        dishService.updateById(dishDto);
        Long dishId = dishDto.getId();
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
        dishFlavorService.remove(lambdaQueryWrapper);
        List<DishFlavor> flavorList = dishDto.getFlavors();
        flavorList.forEach(
                dishFlavor -> {
                    dishFlavor.setDishId(dishId);
                    dishFlavor.setId(null);
                    dishFlavorService.save(dishFlavor);
                }
        );
        String key = "dish_" + dishDto.getId() + "_" + dishDto.getStatus();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }

        return CommonReturn.success("Update dish success");

    }

    @Override
    public CommonReturn<?> getByCategoryId(Long categoryId, Integer status) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(categoryId != null, Dish::getCategoryId, categoryId);
        lambdaQueryWrapper.eq(status != null, Dish::getStatus, status);
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(lambdaQueryWrapper);

        ArrayList<DishDto> dishDtoArrayList = new ArrayList<>();

        for (Dish dish : dishList) {
            DishDto dishDto = new DishDto();
            String key = "dish_" + dish.getId() + "_" + dish.getStatus();
            boolean is_Cache = Boolean.TRUE.equals(redisTemplate.hasKey(key));
            if (is_Cache) {
                dishDto = (DishDto) redisTemplate.opsForValue().get(key);
            } else {
                BeanUtils.copyProperties(dish, dishDto);
                Category category = categoryService.getById(dish.getCategoryId());
                dishDto.setCategoryName(category.getName());
                LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
                dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
                List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
                dishDto.setFlavors(dishFlavorList);
                redisTemplate.opsForValue().set(key, dishDto, 300, TimeUnit.SECONDS);
            }
            dishDtoArrayList.add(dishDto);
        }
        return CommonReturn.success(dishDtoArrayList);

    }

    @Override
    public CommonReturn<?> deleteDish(List<Long> ids) {

        for (Long dishId : ids) {
            Dish dish = dishService.getById(dishId);
            if (dish.getStatus() == 1) {
                return CommonReturn.error(dish.getName() + "未被停售，不能删除");
            }
            LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SetmealDish::getDishId, dishId);
            List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);

            if (list.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                list.forEach(
                        setmealDish -> {
                            Setmeal setmeal = setmealService.getById(setmealDish.getSetmealId());
                            stringBuilder.append(setmeal.getName());
                            stringBuilder.append(" ");

                        }
                );

                return CommonReturn.error("当前菜品包含在" + stringBuilder + "中，不能删除");
            }
        }
        if (dishService.removeByIds(ids)) {
            return CommonReturn.success("菜品删除成功");
        } else {
            return CommonReturn.error("菜品删除失败");
        }


    }

    @Override
    public CommonReturn<?> setDishStatus(List<Long> ids, Integer status) {
        if (status == 1) {
            List<Dish> dishList = dishService.listByIds(ids);
            dishList.forEach(dish -> dish.setStatus(status));
            dishService.updateBatchById(dishList);
        }

        if (status == 0) {
            List<Dish> dishList = dishService.listByIds(ids);
            //检查套餐是否包含菜品
            for (Dish dish : dishList) {
                Long id = dish.getId();
                LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(SetmealDish::getDishId, id);
                List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
                if (list.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (SetmealDish setmealDish : list) {
                        Setmeal setmeal = setmealService.getById(setmealDish.getSetmealId());
                        stringBuilder.append(setmeal.getName());
                        stringBuilder.append(" ");
                    }
                    return CommonReturn.error("当前菜品包含在" + stringBuilder + "中，不能删除");
                }
            }
            dishList.forEach(dish -> dish.setStatus(status));
            dishService.updateBatchById(dishList);

        }
        return CommonReturn.success("更新菜品状态成功");
    }
}

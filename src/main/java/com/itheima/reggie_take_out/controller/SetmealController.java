package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.dto.SetmealDto;
import com.itheima.reggie_take_out.service.SetmealDtoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/16
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Autowired
    private SetmealDtoService setmealDtoService;

    @CacheEvict(value = "setmealCache", allEntries = true)
    @PostMapping
    public CommonReturn<?> save(@RequestBody SetmealDto setmealDto) {
        return setmealDtoService.saveWithDish(setmealDto);
    }

    @GetMapping("/page")
    public CommonReturn<?> page(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String name) {
        return setmealDtoService.page(page, pageSize, name);
    }

    @CacheEvict(value = "setmealCache", allEntries = true)
    @DeleteMapping
    public CommonReturn<?> delete(@RequestParam List<Long> ids) {
        return setmealDtoService.deleteByIds(ids);
    }


    @PostMapping("/status/{status}")
    public CommonReturn<?> updateStatus(@RequestParam List<Long> ids, @PathVariable Integer status) {
        return setmealDtoService.updateStatus(ids, status);
    }

    @Cacheable(value = "setmealCache", key = "#categoryId+'_'+#status")
    @GetMapping("/list")
    public CommonReturn<?> getByCategoryId(@RequestParam Long categoryId, @RequestParam Integer status) {
        return setmealDtoService.getByCategoryId(categoryId, status);
    }

    @GetMapping("/{id}")
    public CommonReturn<?> getSetmealById(@PathVariable Long id) {
        return setmealDtoService.getSetmealById(id);
    }

    @CacheEvict(value = "setmealCache", allEntries = true)
    @PutMapping
    public CommonReturn<?> updateSetmeal(@RequestBody SetmealDto setmealDto) {
        return setmealDtoService.updateSetmeal(setmealDto);
    }
}

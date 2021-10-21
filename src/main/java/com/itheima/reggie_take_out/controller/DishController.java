package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.dto.DishDto;
import com.itheima.reggie_take_out.service.DishDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/15
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishDtoService dishDtoService;

    @PostMapping
    public CommonReturn<?> addWithFlavor(@RequestBody DishDto dishDto) {
        return dishDtoService.saveWithFlavor(dishDto);
    }

    @RequestMapping("/page")
    public CommonReturn<?> list(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String name) {
        return dishDtoService.list(page, pageSize, name);
    }

    @GetMapping("/{id}")
    public CommonReturn<?> getById(@PathVariable Long id) {
        return dishDtoService.getByIdWithFlavor(id);
    }

    @PutMapping
    public CommonReturn<?> updateWithFlavors(@RequestBody DishDto dishDto) {
        return dishDtoService.updateWithFlavors(dishDto);
    }

    @GetMapping("/list")
    public CommonReturn<?> getByCategoryId(@RequestParam Long categoryId, @RequestParam(required = false) Integer status) {
        return dishDtoService.getByCategoryId(categoryId, status);
    }

    @DeleteMapping
    public CommonReturn<?> deleteDish(@RequestParam List<Long> ids) {
        return dishDtoService.deleteDish(ids);
    }

    @PostMapping("/status/{status}")
    public CommonReturn<?> setDishStatus(@RequestParam List<Long> ids, @PathVariable Integer status) {
        return dishDtoService.setDishStatus(ids, status);
    }

}

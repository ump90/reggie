package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Category;
import com.itheima.reggie_take_out.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author UMP90
 * @date 2021/10/14
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public CommonReturn<?> page(@RequestParam int page, @RequestParam int pageSize) {
        return categoryService.getPage(page, pageSize);
    }

    @PostMapping
    public CommonReturn<?> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping
    public CommonReturn<?> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping
    public CommonReturn<?> deleteCategory(@RequestParam Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/list")
    public CommonReturn<?> list(Long type) {
        return categoryService.list(type);
    }


}

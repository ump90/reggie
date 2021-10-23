package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Employee;
import com.itheima.reggie_take_out.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


/**
 * @author UMP90
 * @date 2021/10/11
 */
@RestController
@Api(tags = "管理人员接口")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public CommonReturn<?> login(ServletRequest request, @RequestBody Employee employee) {
        return employeeService.login((HttpServletRequest) request, employee);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public CommonReturn<?> logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }

    @ApiOperation("添加管理人员")
    @PostMapping
    public CommonReturn<?> addEmployee(ServletRequest request, @RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("管理人员分页查询")
    @GetMapping("/page")
    public CommonReturn<?> page(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) String name) {
        return employeeService.page(page, pageSize, name);
    }

    @ApiOperation("更新管理人员信息")
    @PutMapping
    public CommonReturn<?> updateEmployee(@RequestBody Employee employee, ServletRequest servletRequest) {
        return employeeService.updateEmployee(employee);
    }

    @ApiOperation("根据ID查询管理人员信息")
    @GetMapping("/{id}")
    public CommonReturn<?> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }


}

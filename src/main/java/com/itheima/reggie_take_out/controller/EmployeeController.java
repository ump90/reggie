package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Employee;
import com.itheima.reggie_take_out.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


/**
 * @author UMP90
 * @date 2021/10/11
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public CommonReturn<?> login(ServletRequest request, @RequestBody Employee employee) {
        return employeeService.login((HttpServletRequest) request, employee);
    }

    @PostMapping("/logout")
    public CommonReturn<?> logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }

    @PostMapping
    public CommonReturn<?> addEmployee(ServletRequest request, @RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/page")
    public CommonReturn<?> page(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) String name) {
        return employeeService.page(page, pageSize, name);
    }

    @PutMapping
    public CommonReturn<?> updateEmployee(@RequestBody Employee employee, ServletRequest servletRequest) {
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/{id}")
    public CommonReturn<?> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }


}

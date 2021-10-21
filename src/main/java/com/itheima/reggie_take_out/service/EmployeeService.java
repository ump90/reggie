package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * @author UMP90
 * @date 2021/10/11
 */

public interface EmployeeService extends IService<Employee> {
    CommonReturn<?> login(HttpServletRequest httpServletRequest, Employee employee);

    CommonReturn<?> logout(HttpServletRequest httpServletRequest);

    CommonReturn<?> addEmployee(Employee employee);

    CommonReturn<?> page(int page, int pageSize, String name);

    CommonReturn<?> updateEmployee(Employee employee);

    CommonReturn<?> getEmployeeById(Long id);

}

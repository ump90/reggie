package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.Employee;
import com.itheima.reggie_take_out.mapper.EmployeeMapper;
import com.itheima.reggie_take_out.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author UMP90
 * @date 2021/10/11
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public CommonReturn<?> login(HttpServletRequest request, Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername())
                .eq(Employee::getPassword, password);
        Employee returnedEmployee = this.getOne(lambdaQueryWrapper);


        if (returnedEmployee == null) {
            return CommonReturn.error("用户名或密码错误");
        }

        if (returnedEmployee.getStatus() == 0) {
            return CommonReturn.error("账户被禁用");
        }

        request.getSession().setAttribute("employee", returnedEmployee.getId());
        return CommonReturn.success(employee);

    }

    @Override
    public CommonReturn<?> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("employee");
        return CommonReturn.success("退出成功");
    }

    @Override
    public CommonReturn<?> addEmployee(Employee employee) {

        String initialPassword = DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
        employee.setPassword(initialPassword);
        employee.setStatus(1);

        if (this.save(employee)) {
            return CommonReturn.success("添加employee成功");
        } else {
            return CommonReturn.error("添加employee失败");
        }
    }

    @Override
    public CommonReturn<?> page(int page, int pageSize, String name) {
        Page<Employee> employeePage = new Page<Employee>(page, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(!"".equals(name) && name != null, Employee::getName, name);
        this.page(employeePage, lambdaQueryWrapper);
        return CommonReturn.success(employeePage);
    }

    @Override
    public CommonReturn<?> updateEmployee(Employee employee) {

        if (this.updateById(employee)) {
            return CommonReturn.success("更新Employee信息成功");
        } else {
            return CommonReturn.error("更新Employee信息失败");
        }
    }

    @Override
    public CommonReturn<?> getEmployeeById(Long id) {
        Employee employee = this.getById(id);
        if (employee != null) {
            return CommonReturn.success(employee);
        } else {
            return CommonReturn.error("id错误，未查询到结果");
        }
    }
}
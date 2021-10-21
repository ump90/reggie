package com.itheima.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.entity.User;

import javax.servlet.http.HttpSession;

/**
 * @author UMP90
 * @date 2021/10/17
 */

public interface UserService extends IService<User> {
    CommonReturn<?> sendMessage(String phone, HttpSession session);

    CommonReturn<?> login(String phone, String inputCode, HttpSession session);

}

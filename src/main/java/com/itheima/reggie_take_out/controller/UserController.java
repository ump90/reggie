package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public CommonReturn<?> sendMessage(@RequestBody Map<String, String> map, HttpSession httpSession) {
        String phone = map.get("phone");
        return userService.sendMessage(phone, httpSession);
    }

    @PostMapping("/login")
    public CommonReturn<?> login(@RequestBody Map<String, String> map, HttpSession httpSession) {
        String phone = map.get("phone");
        String code = map.get("code");
        if (StringUtils.isBlank(code)) {
            return CommonReturn.error("验证码不能为空");
        }
        return userService.login(phone, code, httpSession);

    }


}

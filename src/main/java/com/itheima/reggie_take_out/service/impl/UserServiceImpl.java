package com.itheima.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.common.ValidateCodeUtils;
import com.itheima.reggie_take_out.entity.User;
import com.itheima.reggie_take_out.mapper.UserMapper;
import com.itheima.reggie_take_out.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public CommonReturn<?> sendMessage(String phone, HttpSession session) {
        if (!StringUtils.isBlank(phone)) {
            String code = ValidateCodeUtils.generateCode(6);
            log.info("验证码:" + phone + "->" + code);

//            SmsUtils.sendSms(phone, "瑞吉外卖", "", code);
//            session.setAttribute("code", code);
//            session.setAttribute("phone", phone);
            redisTemplate.opsForValue().set(phone, code, 300, TimeUnit.SECONDS);

            return CommonReturn.success("短信发送成功");
        } else {
            return CommonReturn.error("手机号不能为空");
        }
    }

    @Override
    public CommonReturn<?> login(String phone, String inputCode, HttpSession session) {

//        String generatedCode = (String) session.getAttribute("code");
        String generatedCode = (String) redisTemplate.opsForValue().get(phone);
        if (generatedCode == null) {
            return CommonReturn.error("登陆失败");
        }


        if (generatedCode.equals(inputCode)) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            User oldUser = this.getOne(lambdaQueryWrapper);
            if (oldUser != null) {
                session.setAttribute("user", oldUser.getId());
                redisTemplate.delete(phone);
            } else {
                User newUser = new User();
                newUser.setName("新用户" + phone);
                newUser.setPhone(phone);
                newUser.setStatus(1);
                this.save(newUser);
                User savedUser = this.getOne(lambdaQueryWrapper);
                session.setAttribute("user", savedUser.getId());
                redisTemplate.delete(phone);
            }
            return CommonReturn.success("登陆成功");
        } else {
            return CommonReturn.error("验证码错误");
        }


    }
}

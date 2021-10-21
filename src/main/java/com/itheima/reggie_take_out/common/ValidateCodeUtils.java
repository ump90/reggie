package com.itheima.reggie_take_out.common;

import java.util.Random;

/**
 * @author UMP90
 * @date 2021/10/17
 */

public class ValidateCodeUtils {
    public static String generateCode(Integer length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();

    }
}

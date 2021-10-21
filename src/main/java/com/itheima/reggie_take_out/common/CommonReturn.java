package com.itheima.reggie_take_out.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author UMP90
 * @date 2021/10/12
 * Common return result
 */
@Data
public class CommonReturn<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map<String, Object> map = new HashMap<String, Object>();

    public static <K> CommonReturn<K> success(K returnObject) {
        CommonReturn<K> commonReturn = new CommonReturn<>();
        commonReturn.data = returnObject;
        commonReturn.code = 1;
        return commonReturn;
    }

    public static <K> CommonReturn<K> error(String message) {
        CommonReturn<K> commonReturn = new CommonReturn<K>();
        commonReturn.code = 0;
        commonReturn.msg = message;
        return commonReturn;
    }

    public CommonReturn<T> add(String key, Object object) {
        this.map.put(key, object);
        return this;
    }


}

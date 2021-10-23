package com.itheima.reggie_take_out.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author UMP90
 * @date 2021/10/12
 * Common return result
 */
@Data
@ApiModel
public class CommonReturn<T> implements Serializable {
    private static final long serialVersionUID = -442693984497024602L;
    @ApiModelProperty("响应码")
    private Integer code;
    @ApiModelProperty("消息")
    private String msg;
    @ApiModelProperty("数据")
    private T data;
    @ApiModelProperty("动态数据")
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

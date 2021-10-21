package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Data
@TableName("shopping_cart")
public class ShoppingCart {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;
    private String image;

    private Long userId;

    private Long dishId;

    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private Double amount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

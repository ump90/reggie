package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Data
@TableName("order_detail")
public class OrderDetail {
    @TableId(type = IdType.ASSIGN_ID)

    private Long id;
    private String name;
    private String image;

    private Long orderId;

    private Long dishId;

    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private Double amount;

}

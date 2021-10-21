package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Data
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 9149098635388614717L;
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

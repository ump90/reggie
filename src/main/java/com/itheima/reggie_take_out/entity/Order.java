package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@TableName("orders")
@Data
public class Order {

    @TableId(type = IdType.ASSIGN_ID)

    private Long id;
    private String number;
    private Integer status;

    private Long userId;

    private Long addressBookId;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;
    private Integer payMethod;
    private Double amount;
    private String remark;
    private String phone;
    private String address;
    private String userName;
    private String consignee;


}

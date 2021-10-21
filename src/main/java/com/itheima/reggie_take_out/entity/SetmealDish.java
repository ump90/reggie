package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/16
 */
@Data
@TableName("setmeal_dish")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = -3499369889147824080L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long setmealId;
    private String dishId;
    private String name;
    private Double price;
    private Integer copies;
    private Integer sort;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT)
    private Long updateUser;

    @TableLogic
    private Integer isDeleted;


}

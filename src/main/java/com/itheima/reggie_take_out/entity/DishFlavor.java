package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/15
 */
@Data
@TableName("dish_flavor")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 5673206718964702261L;
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dishId;
    private String name;
    private String value;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    @TableLogic
    private Integer isDeleted;


}

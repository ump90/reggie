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
@TableName("dish")
public class Dish implements Serializable {

    private static final long serialVersionUID = 6941186285535391635L;
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    private String name;


    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    private Double price;
    private String code;
    private String image;
    private String description;
    private Integer status;
    private Integer sort;
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

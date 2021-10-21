package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/14
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = -8157390402379589741L;
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer type;
    private String name;
    private Integer sort;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime updateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}

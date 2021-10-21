package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author UMP90
 * @date 2021/10/14
 */
@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1454706359007401249L;
    @TableId(type = IdType.ASSIGN_ID)

    private Long id;

    private Long userId;
    private String consignee;
    private String sex;
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
    @TableLogic
    private String isDeleted;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)

    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}

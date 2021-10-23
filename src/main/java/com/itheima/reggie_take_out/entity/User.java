package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Data
@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = 1338803395301956188L;
    @TableId
    @ApiModelProperty("编号")
    private Long id;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("身份证")
    private String idNumber;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("状态")
    private Integer status;
}

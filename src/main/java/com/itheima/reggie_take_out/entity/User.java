package com.itheima.reggie_take_out.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1338803395301956188L;
    @TableId

    private Long id;

    private String name;
    private String phone;
    private String sex;
    private String idNumber;
    private String avatar;
    private Integer status;
}

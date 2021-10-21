package com.itheima.reggie_take_out.dto;

import com.itheima.reggie_take_out.entity.Dish;
import com.itheima.reggie_take_out.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/15
 */
@Data

public class DishDto extends Dish implements Serializable {

    private static final long serialVersionUID = -3633649058247096641L;
    private List<DishFlavor> flavors = new ArrayList<>();
    private Integer copies;
    private String categoryName;


}

package com.itheima.reggie_take_out.dto;

import com.itheima.reggie_take_out.entity.Setmeal;
import com.itheima.reggie_take_out.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/16
 */
@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String categoryName;

}

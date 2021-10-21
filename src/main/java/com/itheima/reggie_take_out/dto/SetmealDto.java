package com.itheima.reggie_take_out.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.reggie_take_out.entity.Setmeal;
import com.itheima.reggie_take_out.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/16
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetmealDto extends Setmeal implements Serializable {

    private static final long serialVersionUID = -1213014969915542111L;
    private List<SetmealDish> setmealDishes;
    private String categoryName;

}

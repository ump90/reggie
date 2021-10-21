package com.itheima.reggie_take_out.dto;

import com.itheima.reggie_take_out.entity.Order;
import com.itheima.reggie_take_out.entity.OrderDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/18
 */
@Data
public class OrderDto extends Order implements Serializable {
    private static final long serialVersionUID = 2625762302336661959L;
    private List<OrderDetail> orderDetails;
    Integer sumNum;
}

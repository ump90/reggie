package com.itheima.reggie_take_out.dto;

import com.itheima.reggie_take_out.entity.Order;
import com.itheima.reggie_take_out.entity.OrderDetail;
import lombok.Data;

import java.util.List;

/**
 * @author UMP90
 * @date 2021/10/18
 */
@Data
public class OrderDto extends Order {
    private List<OrderDetail> orderDetails;
    Integer sumNum;
}

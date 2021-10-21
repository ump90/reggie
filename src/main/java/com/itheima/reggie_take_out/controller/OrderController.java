package com.itheima.reggie_take_out.controller;

import com.itheima.reggie_take_out.common.CommonReturn;
import com.itheima.reggie_take_out.service.OrderDtoService;
import com.itheima.reggie_take_out.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author UMP90
 * @date 2021/10/17
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDtoService orderDtoService;

    @PostMapping("/submit")
    public CommonReturn<?> submit(@RequestBody Map<String, String> map) {
        String remark = map.get("remark");
        Integer payMethod = Integer.parseInt(map.get("payMethod"));
        Long addressBookId = Long.parseLong(map.get("addressBookId"));
        log.info("订单信息： " + remark + " " + payMethod + " " + addressBookId);
        return orderService.submitOrder(remark, payMethod, addressBookId);
    }

    @GetMapping("/userPage")
    public CommonReturn<?> userPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return orderDtoService.userPage(page, pageSize);
    }

    //    /order/page?page=1&pageSize=10
    @GetMapping("/page")
    public CommonReturn<?> page(@RequestParam Integer page,
                                @RequestParam Integer pageSize,
                                @RequestParam(required = false) LocalDateTime beginTime,
                                @RequestParam(required = false) LocalDateTime endTime,
                                @RequestParam(required = false) String number) {
        return orderService.pageOrder(page, pageSize, beginTime, endTime, number);
    }

    @PutMapping
    public CommonReturn<?> updateStatus(@RequestBody Map<String, String> map) {
        Long id = Long.parseLong(map.get("id"));
        Integer status = Integer.parseInt(map.get("status"));
        return orderService.updateStatus(id, status);
    }


}

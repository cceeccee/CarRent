package com.zr.controller.lease;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.lease.OrderService;
import com.zr.util.AjaxResult;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lease/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public String list(@RequestBody Order order){
        List<Order> orderList = orderService.list(order);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));
    }



    ////////////////////
    @RequestMapping("/getCarNum")
    public String getCarNum(@RequestBody Param input){
        Map map = orderService.getCarNum(input.startDate, input.endDate);
        return JSON.toJSONString(AjaxResult.success("查询成功",map));
    }

    @RequestMapping("/getIncomeSum")
    public String getIncomeSum(@RequestBody Param input){
        BigDecimal sum = orderService.getIncomeSum(input.startDate, input.endDate);
        return JSON.toJSONString(AjaxResult.success("查询成功", sum));
    }

    @RequestMapping("/getDailySum")
    public String getDailySum(@RequestBody Param input){
        Map map = orderService.getDailyIncome(input.startDate, input.endDate);
        return JSON.toJSONString(AjaxResult.success("查询成功",map));
    }
}


class Param{
    Order startDate;
    Order endDate;

    public Order getStartDate() {
        return startDate;
    }

    public void setStartDate(Order startDate) {
        this.startDate = startDate;
    }

    public Order getEndDate() {
        return endDate;
    }

    public void setEndDate(Order endDate) {
        this.endDate = endDate;
    }
}
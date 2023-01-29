package com.zr.service.lease;

import com.zr.vo.lease.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> list(Order order);

    ////
    Map getCarNum(Order startDate, Order endDate);

    BigDecimal getIncomeSum(Order startDate, Order endDate);

    Map getDailyIncome(Order startDate, Order endDate);
}

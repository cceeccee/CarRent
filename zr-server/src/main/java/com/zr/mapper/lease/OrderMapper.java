package com.zr.mapper.lease;

import com.zr.vo.car.Car;
import com.zr.vo.lease.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> list(Order order);

    List<Car> selectByOrderTime(String startDate, String endDate);

    Order getSumByPayTime(String startDate, String endDate);

    List<Order> getDailyByPayTime(String startDate, String endDate);
}
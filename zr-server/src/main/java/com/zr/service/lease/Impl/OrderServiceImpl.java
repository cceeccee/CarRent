package com.zr.service.lease.Impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.car.WorkMapper;
import com.zr.mapper.lease.OrderMapper;
import com.zr.service.lease.OrderService;
import com.zr.util.RedisUtils;
import com.zr.util.TokenUtil;
import com.zr.vo.car.Car;
import com.zr.vo.car.Work;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private OrderMapper orderMapper;


    @Override
    public List<Order> list(Order order) {
        PageHelper.startPage(order.getPageNum(),order.getPageSize());
        return orderMapper.list(order);
    }


    ////////


    @Autowired
    private WorkMapper workMapper;

    @Override
    public Map getCarNum(Order startDate, Order endDate) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        List<Car> carList = orderMapper.selectByOrderTime(spf.format(startDate.getOrderTime()),spf.format(endDate.getOrderTime()));
        Map map = new HashMap<String, Long>();
        Long tmp ;
        for(int i=0;i<carList.size();i++){
            if(map.containsKey(carList.get(i).getName())){
                tmp = (Long) map.get(carList.get(i).getName());
                map.put(carList.get(i).getName(), tmp+1);
            }else{
                map.put(carList.get(i).getName(), 1L);
            }
        }
        return map;
    }

    @Override
    public BigDecimal getIncomeSum(Order startDate, Order endDate) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        BigDecimal sum = orderMapper.getSumByPayTime(spf.format(startDate.getPayTime()),spf.format(endDate.getPayTime())).getTotal();
        sum = sum.add(workMapper.getSumByOutFitTime(spf.format(startDate.getPayTime()),spf.format(endDate.getPayTime())).getTotal()) ;
        return sum;
    }

    @Override
    public Map getDailyIncome(Order startDate, Order endDate) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        List<Order> orderList = orderMapper.getDailyByPayTime(spf.format(startDate.getPayTime()),spf.format(endDate.getPayTime()));
        Map map = new HashMap<Date, BigDecimal>();
        List<Work> workList = workMapper.getDailyByOutFitTime(spf.format(startDate.getPayTime()),spf.format(endDate.getPayTime()));
        for(int i=0;i<orderList.size();i++){
            if(orderList.get(i).getTotal()!=null){
                map.put(spf.format(orderList.get(i).getPayTime()),orderList.get(i).getTotal());
            }
        }
        BigDecimal tmp;
        for(int i=0;i<workList.size();i++){
            if(map.containsKey(workList.get(i).getOutFitTime()) && orderList.get(i).getTotal() != null){
                tmp = (BigDecimal) map.get(workList.get(i).getOutFitTime());
                map.put(spf.format(workList.get(i).getOutFitTime()), tmp.add(orderList.get(i).getTotal()));
            }
        }
        return map;
    }


}
package com.zr.vo.car;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.vo.BaseVo;
import com.zr.vo.lease.Customer;
import com.zr.vo.lease.Order;
import com.zr.vo.shop.Shop;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Work extends BaseVo {
    private Long id;

    private Long orderId;

    private String carNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;

    private String outUserId;

    private String inUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date outFitTime;

    private String outFitUserId;

    private String status;

    private BigDecimal total;

    private String mark;

    ////////
    private Order order;

    private Customer customer;

    private Car car;

    private Shop shop;
    ////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId == null ? null : outUserId.trim();
    }

    public String getInUserId() {
        return inUserId;
    }

    public void setInUserId(String inUserId) {
        this.inUserId = inUserId == null ? null : inUserId.trim();
    }

    public Date getOutFitTime() {
        return outFitTime;
    }

    public void setOutFitTime(Date outFitTime) {
        this.outFitTime = outFitTime;
    }

    public String getOutFitUserId() {
        return outFitUserId;
    }

    public void setOutFitUserId(String outFitUserId) {
        this.outFitUserId = outFitUserId == null ? null : outFitUserId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", carNo='" + carNo + '\'' +
                ", outTime=" + outTime +
                ", inTime=" + inTime +
                ", outUserId='" + outUserId + '\'' +
                ", inUserId='" + inUserId + '\'' +
                ", outFitTime=" + outFitTime +
                ", outFitUserId='" + outFitUserId + '\'' +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", mark='" + mark + '\'' +
                ", order=" + order +
                ", customer=" + customer +
                ", car=" + car +
                ", shop=" + shop +
                '}';
    }
}
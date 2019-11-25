package com.tencent.health.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约数据设置
 */
public class OrderSetting implements Serializable {
    private Integer id;

    private @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    Date orderDate;
    private Integer number;
    private Integer reservations;

    public OrderSetting() {
    }

    public OrderSetting(Date orderDate, Integer number) {
        this.orderDate = orderDate;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }
}

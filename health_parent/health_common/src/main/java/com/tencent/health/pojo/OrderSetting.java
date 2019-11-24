package com.tencent.health.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderSetting implements Serializable {
    private Integer id;
    private Date orderDate;
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

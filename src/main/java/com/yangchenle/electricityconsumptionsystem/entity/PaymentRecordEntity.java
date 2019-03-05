package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentRecordEntity {

    private Integer paymentId;

    private Integer electricId;

    private Integer paymentMethod;

    private BigDecimal money;

    private Integer paymentState;

    private Date addTime;

    private Date updateTime;
}

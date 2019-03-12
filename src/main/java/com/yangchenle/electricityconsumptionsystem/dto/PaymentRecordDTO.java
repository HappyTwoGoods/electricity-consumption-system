package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentRecordDTO implements Serializable {

    private static final long serialVersionUID = 305308744114805037L;
    private Integer paymentId;

    private Integer electricId;

    private Integer paymentMethod;

    private BigDecimal money;

    private Integer paymentState;

    private Date addTime;

    private Date updateTime;
}

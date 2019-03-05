package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ElectricEntity {

    private Integer electricId;

    private Double price;

    private Integer type;

    private Double lastData;

    private Integer userId;

    private BigDecimal money;

    private Date addTime;

    private Date updateTime;
}

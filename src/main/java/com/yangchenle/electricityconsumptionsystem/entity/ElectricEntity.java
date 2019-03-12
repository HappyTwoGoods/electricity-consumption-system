package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ElectricEntity {

    private Integer electricId;

    private Integer type;

    private BigDecimal lastData;

    private Integer userId;

    private BigDecimal money;

    private Integer state;

    private Date addTime;

    private Date updateTime;
}

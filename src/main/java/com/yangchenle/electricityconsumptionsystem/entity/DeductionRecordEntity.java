package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DeductionRecordEntity {

    private Integer id;

    private Integer electricId;

    private BigDecimal electricConsumption;

    private BigDecimal money;

    private Date addTime;

    private Date updateTime;
}

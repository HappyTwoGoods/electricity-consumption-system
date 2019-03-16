package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyAndConsumptionSumEntity {

    private Integer electricId;

    private BigDecimal money;

    private BigDecimal consumption;
}

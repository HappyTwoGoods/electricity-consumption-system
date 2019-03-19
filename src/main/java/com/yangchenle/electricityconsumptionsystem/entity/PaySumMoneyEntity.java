package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaySumMoneyEntity {

    Integer electricId;

    BigDecimal money;
}

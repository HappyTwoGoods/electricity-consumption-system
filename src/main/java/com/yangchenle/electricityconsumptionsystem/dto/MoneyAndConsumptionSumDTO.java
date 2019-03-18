package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MoneyAndConsumptionSumDTO implements Serializable {

    private static final long serialVersionUID = 6961220453101294578L;

    private Integer electricId;

    private BigDecimal money;

    private BigDecimal consumption;
}

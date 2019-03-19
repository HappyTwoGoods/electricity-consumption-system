package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PaySumMoneyDTO implements Serializable {
    private static final long serialVersionUID = 9043888840864595431L;

    Integer electricId;

    BigDecimal money;
}

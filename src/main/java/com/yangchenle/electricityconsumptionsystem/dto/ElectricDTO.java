package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ElectricDTO implements Serializable {

    private static final long serialVersionUID = 912846626837657014L;
    private Integer electricId;

    private Double price;

    private Integer type;

    private Double lastData;

    private Integer userId;

    private BigDecimal money;

    private Date addTime;

    private Date updateTime;
}

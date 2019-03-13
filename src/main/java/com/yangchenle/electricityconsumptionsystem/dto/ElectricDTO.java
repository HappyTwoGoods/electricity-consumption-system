package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ElectricDTO implements Serializable {

    private static final long serialVersionUID = 912846626837657014L;
    private Integer electricId;

    private Integer num;

    private Integer type;

    private BigDecimal lastData;

    private Integer userId;

    private BigDecimal money;

    private Integer state;

    private Date addTime;

    private Date updateTime;
}

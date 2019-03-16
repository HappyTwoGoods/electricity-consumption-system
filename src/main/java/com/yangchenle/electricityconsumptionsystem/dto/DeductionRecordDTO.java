package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DeductionRecordDTO implements Serializable {

    private static final long serialVersionUID = -6798968785400397742L;

    private Integer id;

    private Integer electricId;

    private BigDecimal electricConsumption;

    private BigDecimal money;

    private Date addTime;

    private Date updateTime;
}

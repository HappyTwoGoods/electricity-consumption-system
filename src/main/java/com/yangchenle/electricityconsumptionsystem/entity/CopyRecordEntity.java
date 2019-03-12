package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CopyRecordEntity {

    private Integer copyId;

    private Integer electricId;

    private BigDecimal copyData;

    private Date addTime;

    private Date updateTime;
}

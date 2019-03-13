package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TypeTableEntity {

    private Integer typeId;

    private String typeNum;

    private BigDecimal price;

    private Date addTime;

    private Date updateTime;
}

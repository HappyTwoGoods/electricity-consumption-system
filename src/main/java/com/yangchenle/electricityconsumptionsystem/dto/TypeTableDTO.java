package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TypeTableDTO implements Serializable {

    private Integer typeId;

    private String typeName;

    private BigDecimal price;

    private Date addTime;

    private Date updateTime;
}

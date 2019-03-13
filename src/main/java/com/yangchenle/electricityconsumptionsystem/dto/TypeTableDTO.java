package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TypeTableDTO implements Serializable {

    private static final long serialVersionUID = -3587309779254757299L;
    private Integer typeId;

    private String typeNum;

    private BigDecimal price;

    private Date addTime;

    private Date updateTime;
}

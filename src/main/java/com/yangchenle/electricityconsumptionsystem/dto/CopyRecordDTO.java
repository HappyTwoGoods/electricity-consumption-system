package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CopyRecordDTO implements Serializable{

    private static final long serialVersionUID = 5087416801424679059L;
    private Integer copyId;

    private Integer electricId;

    private BigDecimal copyData;

    private Date addTime;

    private Date updateTime;
}

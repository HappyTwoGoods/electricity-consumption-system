package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CopyRecordEntity {

    private Integer copyId;

    private Integer electricId;

    private Double copyData;

    private Date addTime;

    private Date updateTime;
}

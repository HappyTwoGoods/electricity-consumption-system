package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CopyRecordDTO implements Serializable{

    private Integer copyId;

    private Integer electricId;

    private Double copyData;

    private Date addTime;

    private Date updateTime;
}

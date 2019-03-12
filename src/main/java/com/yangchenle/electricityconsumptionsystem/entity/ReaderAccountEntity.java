package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReaderAccountEntity {

    private Integer readerId;

    private String readerName;

    private String readerPhone;

    private Date addTime;

    private Date updateTime;
}

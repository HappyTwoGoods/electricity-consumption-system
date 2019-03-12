package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AdminEntity {

    private Integer adminId;

    private String adminName;

    private String adminPhone;

    private String idCard;

    private Date addTime;

    private Date updateTime;
}

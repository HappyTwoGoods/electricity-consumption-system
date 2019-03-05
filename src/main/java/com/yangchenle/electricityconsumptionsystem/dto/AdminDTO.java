package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminDTO implements Serializable{

    private Integer adminId;

    private String adminName;

    private String password;

    private String adminPhone;

    private String idCard;

    private Date addTime;

    private Date updateTime;
}

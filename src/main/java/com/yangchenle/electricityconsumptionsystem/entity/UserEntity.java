package com.yangchenle.electricityconsumptionsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserEntity {

    private Integer userId;

    private String userName;

    private String userPhone;

    private Integer userAccount;

    private String userAddress;

    private String idCard;

    private Date addTime;

    private Date updateTime;

    private BigDecimal price;
}

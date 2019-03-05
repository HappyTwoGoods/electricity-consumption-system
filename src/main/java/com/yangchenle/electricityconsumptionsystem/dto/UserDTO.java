package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {

    private Integer userId;

    private String userName;

    private String password;

    private Integer userAccount;

    private String userAddress;

    private String idCard;

    private Date addTime;

    private Date updateTime;
}

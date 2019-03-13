package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 7323006598800832850L;
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

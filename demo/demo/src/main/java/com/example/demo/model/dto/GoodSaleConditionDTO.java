package com.example.demo.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoodSaleConditionDTO {
    String vipid;

    String goodid;

    String userid;

    Date starttime;

    Date endtime;

    String vipcard;
}

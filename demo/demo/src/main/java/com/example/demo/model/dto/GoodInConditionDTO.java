package com.example.demo.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GoodInConditionDTO {
    String pid;

    String goodid;

    String userid;

    Date starttime;

    Date endtime;
}

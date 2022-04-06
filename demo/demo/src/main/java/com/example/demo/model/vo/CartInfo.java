package com.example.demo.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CartInfo {

    String id;

    String vipId;

    String vipName;

    String goodName;

    String goodId;

    String userId;

    String userName;

    int num;

    float price;

    Date time;
}

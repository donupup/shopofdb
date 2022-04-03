package com.example.demo.model.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GoodSaleInfo {

    String id;

    int num;

    int oneprice;

    int totalprice;

    String goodname;

    String vipname;

    String vipid;

    String username;

    String goodsoldtime;

    String bio;
}

package com.example.demo.model.dto;

import lombok.Data;

@Data
public class GoodEditDTO {

    //必须要id 要通过id去判断
    String id;

    String goodname;

    int pricein;

    int pricesell;

    int storage;

    boolean status;

    String bio;

    String categoryId;

    String providerId;
}

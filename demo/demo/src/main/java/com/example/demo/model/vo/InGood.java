package com.example.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InGood {

    private String id;

    private  String goodname;

    private int num;

    private  String goodId;

    private String bio;

    private Date GoodInTime;

    private  int inPrice;
}

package com.example.demo.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GoodInInfo {
    private String id;

    private int num;

    private String goodName;

    private int goodPrice;

    private String providerName;

    private String userName;

    private String bio;

    private Date goodInTime;
}

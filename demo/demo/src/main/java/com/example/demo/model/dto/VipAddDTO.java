package com.example.demo.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class VipAddDTO {

    private String vname;

    private String vphone;


    private String vaddress;


    private Float vbalance;

    private String vsex;
}

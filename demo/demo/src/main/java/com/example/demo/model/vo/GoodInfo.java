package com.example.demo.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodInfo {

    private String id;

    private String goodname;

    private String categoryName;

    private float pricein;

    private float pricesell;

    private int salenum;

    private int storage;

    private int shelflife;

    private String bio;

    private Date createTime;

    private Date modifyTime;

    private String ProviderNmae;

}

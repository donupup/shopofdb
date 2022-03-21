package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("bms_good_sale")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SaleGood implements Serializable {

    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("goodname")
    private String goodname;

    @TableField("num")
    private int num;

    @TableField("good_id")
    private String goodId;

    @TableField("priceinNow")
    private int priceinNow;

    @TableField("pricesellNow")
    private int pricesellNow;

    @TableField("good_sold_time")
    private Date goodSoldTime;

}

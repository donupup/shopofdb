package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@TableName("bms_good_sale")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodSale {
    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("num")
    private int num;

    @TableField("good_id")
    private String goodId;

    @TableField("user_id")
    private String userId;

    @TableField("vip_id")
    private String vipId;

    @TableField("good_sold_time")
    private Date goodSoldTime;

    @TableField("bio")
    private String bio;
}

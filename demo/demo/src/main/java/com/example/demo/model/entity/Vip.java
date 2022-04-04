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

@Data
@Builder
@TableName("bms_vip")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Vip {
    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "vid", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("vname")
    private String vname;

    @TableField("vphone")
    private String vphone;

    @TableField("vaddress")
    private String vaddress;

    @TableField("vbalance")
    private Float vbalance;

    @TableField("vsex")
    private String vsex;
}

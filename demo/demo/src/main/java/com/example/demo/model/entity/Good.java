package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@TableName("bms_good")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Good implements Serializable {

    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("goodname")
    private String goodname;

    @TableField("category_id")
    private String categoryId;

    @TableField("pricein")
    private float pricein;

    @TableField("pricesell")
    private float pricesell;

    @TableField("salenum")
    private int salenum;

    @TableField("storage")
    private int storage;

    @TableField("shelflife")
    private int shelflife;

    @Builder.Default
    @TableField("bio")
    private String bio = "商品";

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

    @TableField("provider_id")
    private String ProviderId;
}

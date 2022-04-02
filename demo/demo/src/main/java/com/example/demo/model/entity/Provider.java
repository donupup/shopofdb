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
@TableName("bms_provider")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Provider {
    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "pid", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("pname")
    private String pname;

    @TableField("pphone")
    private String pphone;

    @TableField("paddress")
    private String paddress;

    @TableField("plinkman")
    private String plinkman;
}

package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartAddDTO {
    String userid;
    String vipid;
    String goodid;
    int num;
}

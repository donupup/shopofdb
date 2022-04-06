package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartEditDTO {
    String id;
    int num;
}

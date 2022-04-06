package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GoodInAddDTO {

    String pid;

    String goodid;

    String userid;

    int num;

    String bio;
}

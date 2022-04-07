package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GoodStaConditionDTO {

    String goodid;

    Date starttime;

    Date endtime;
}

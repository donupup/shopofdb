package com.example.demo.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sms {

    String phoneNumber;

    String code;
}

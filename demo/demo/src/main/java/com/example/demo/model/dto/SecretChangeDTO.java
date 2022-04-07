package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecretChangeDTO {
    String phoneNumber;

    String code;

    String userid;
}

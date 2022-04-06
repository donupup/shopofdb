package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCenterInfoDTO {

    String userid;

    String username;

    String alias;

    String email;

    String bio;

    String stuNo;
}

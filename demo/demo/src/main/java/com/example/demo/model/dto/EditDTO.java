package com.example.demo.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
public class EditDTO {

    private String username;

    private String stuNo;

    private String alias;

    private String role;

    private String mobile;

    private String email;
}


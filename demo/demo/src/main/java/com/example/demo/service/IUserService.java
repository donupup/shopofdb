package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.EditDTO;
import com.example.demo.model.dto.LoginDTO;
import com.example.demo.model.dto.RegisterDTO;
import com.example.demo.model.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {

    //用户登录
    Map<String, String> executeLogin(LoginDTO dto);

    //根据用户名获得用户
    User getUserByUsername(String username);

    User  executeRegister(RegisterDTO dto);

    List<User> getUserList();

    int executeEdit(EditDTO dto);

    int executeDelete(String username);
}

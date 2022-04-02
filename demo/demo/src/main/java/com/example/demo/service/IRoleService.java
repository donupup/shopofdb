package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRoleService extends IService<Role> {

    List<Role> getRoleList();
}

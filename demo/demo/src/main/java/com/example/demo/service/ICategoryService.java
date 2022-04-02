package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Provider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService extends IService<Category> {

    List<Category> getList();

    String getNameById(String id);
}

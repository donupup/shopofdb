package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Good;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IGoodService extends IService<Good> {

    List<Good> getGoodList();

    int executeDelete(String id);
}

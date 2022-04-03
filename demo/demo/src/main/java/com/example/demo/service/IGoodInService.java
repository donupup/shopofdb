package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.GoodInAddDTO;
import com.example.demo.model.dto.GoodInEditDTO;
import com.example.demo.model.entity.GoodIn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IGoodInService extends IService<GoodIn> {

    List<GoodIn> getlist();

    int executeDelete(String id);

    int executeEdit(GoodInEditDTO dto);

    GoodIn executeAdd(GoodInAddDTO dto);
}

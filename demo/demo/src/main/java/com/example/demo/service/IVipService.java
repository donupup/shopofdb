package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.ProviderAddDTO;
import com.example.demo.model.dto.ProviderEditDTO;
import com.example.demo.model.dto.VipAddDTO;
import com.example.demo.model.dto.VipEditDTO;
import com.example.demo.model.entity.Provider;
import com.example.demo.model.entity.Vip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IVipService  extends IService<Vip> {

    List<Vip> getList();

    Vip executeAdd(VipAddDTO dto);

    int executeDelete(String id);

    int executeEdit(VipEditDTO dto);
}

package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.ProviderAddDTO;
import com.example.demo.model.dto.ProviderConditionDTO;
import com.example.demo.model.dto.ProviderEditDTO;
import com.example.demo.model.entity.Provider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProviderService extends IService<Provider> {

    List<Provider> getList();

    Provider executeAdd(ProviderAddDTO dto);

    int executeDelete(String id);

    int executeEdit(ProviderEditDTO dto);

    String getNameById(String id);

    List<Provider> getCondition(ProviderConditionDTO dto);
}


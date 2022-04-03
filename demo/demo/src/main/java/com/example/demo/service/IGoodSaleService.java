package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.GoodInEditDTO;
import com.example.demo.model.dto.GoodSaleConditionDTO;
import com.example.demo.model.entity.GoodSale;
import com.example.demo.model.vo.GoodSaleInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IGoodSaleService extends IService<GoodSale> {

    List<GoodSale> getlist();

    int executeEdit(GoodInEditDTO dto);

    int executeDelete(String id);

    List<GoodSale> getConditionList(GoodSaleConditionDTO dto);
}

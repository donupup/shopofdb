package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.dto.GoodAddDTO;
import com.example.demo.model.dto.GoodEditDTO;
import com.example.demo.model.dto.goodSaleDTO;
import com.example.demo.model.entity.Good;

import com.example.demo.model.entity.SaleGood;
import com.example.demo.model.vo.InGood;
import com.example.demo.model.vo.OutGood;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface IGoodService extends IService<Good> {

    List<Good> getGoodList();

    int executeDelete(String id);

    int executeEdit(GoodEditDTO dto);

    Good executeAdd(GoodAddDTO dto);

    int outGood(String name, int num, String good_id, String bio, int is_del, Date date);

    int inGood( String name, int num, String good_id, String bio,  Date date, int inPrice);

    int sellGoodToTable(String name, int num, String good_id, int in, int out ,  Date date);

    List<Good> sellGood(List<goodSaleDTO> goodArray);

    Map<String, Integer> getSoldGood();

    List<SaleGood> getSaleList();

    List<InGood> getInList();

    List<OutGood> getOutList();
}

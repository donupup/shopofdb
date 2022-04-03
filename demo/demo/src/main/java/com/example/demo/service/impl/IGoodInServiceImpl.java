package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.GoodInMapper;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.model.dto.GoodInEditDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodIn;
import com.example.demo.service.IGoodInService;
import com.example.demo.service.IGoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IGoodInServiceImpl extends ServiceImpl<GoodInMapper, GoodIn> implements IGoodInService {
    @Override
    public List<GoodIn> getlist() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public int executeDelete(String id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int executeEdit(GoodInEditDTO dto) {
        GoodIn gi = GoodIn.builder().bio(dto.getBio()).num(dto.getNum()).id(dto.getId()).build();
        int res = this.baseMapper.updateById(gi);
        return res;
    }
}

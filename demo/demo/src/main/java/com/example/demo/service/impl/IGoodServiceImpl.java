package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.User;
import com.example.demo.service.IGoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class IGoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

    @Resource
    private GoodMapper goodMapper;


    @Override
    public List<Good> getGoodList() {
        List<Good> goods = goodMapper.selectList(null);
        return goods;
    }

    @Override
    public int executeDelete(String id) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::getId, id);
        int result = baseMapper.delete(wrapper);
        return result;
    }
}

package com.example.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.GoodInMapper;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.model.dto.GoodInAddDTO;
import com.example.demo.model.dto.GoodInConditionDTO;
import com.example.demo.model.dto.GoodInEditDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodIn;
import com.example.demo.service.IGoodInService;
import com.example.demo.service.IGoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public GoodIn executeAdd(GoodInAddDTO dto) {
        GoodIn g = GoodIn.builder().goodId(dto.getGoodid()).bio(dto.getBio()).num(dto.getNum()).providerId(dto.getPid()).userId(dto.getUserid())
                .goodInTime(new Date()).build();
        System.out.println(new Date());
        this.baseMapper.insert(g);
        return g;
    }

    @Override
    public List<GoodIn> getCondition(GoodInConditionDTO dto) {
        QueryWrapper<GoodIn> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<GoodIn> lambda = wrapper.lambda();
        if(!StrUtil.isBlank(dto.getGoodid()))
        {
            lambda.eq(GoodIn::getGoodId,dto.getGoodid());
        }
        if(!StrUtil.isBlank(dto.getPid()))
        {
            lambda.eq(GoodIn::getProviderId,dto.getPid());
        }
        if(!StrUtil.isBlank(dto.getUserid()))
        {
            lambda.eq(GoodIn::getUserId,dto.getUserid());
        }
        if(dto.getStarttime() != null)
        {
            lambda.ge(GoodIn::getGoodInTime,dto.getStarttime());
        }
        if(dto.getEndtime() != null)
        {
            lambda.le(GoodIn::getGoodInTime,dto.getEndtime());
        }
        List<GoodIn> gs = this.baseMapper.selectList(lambda);
        return  gs;
    }
}

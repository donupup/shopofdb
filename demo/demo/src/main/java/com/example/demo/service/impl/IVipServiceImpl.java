package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ProviderMapper;
import com.example.demo.mapper.VipMapper;
import com.example.demo.model.dto.VipAddDTO;
import com.example.demo.model.dto.VipConditionDTO;
import com.example.demo.model.dto.VipEditDTO;
import com.example.demo.model.entity.Provider;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Vip;
import com.example.demo.service.IProviderService;
import com.example.demo.service.IVipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IVipServiceImpl extends ServiceImpl<VipMapper, Vip> implements IVipService {
    @Override
    public List<Vip> getList() {
        return baseMapper.selectList(null);
    }

    @Override
    public Vip executeAdd(VipAddDTO dto) {
        Vip v = Vip.builder()
                .vname(dto.getVname())
                .vphone(dto.getVphone())
                .vbalance(dto.getVbalance())
                .vsex(dto.getVsex())
                .vaddress(dto.getVaddress())
                .build();
        this.baseMapper.insert(v);
        return v;
    }

    @Override
    public int executeDelete(String id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int executeEdit(VipEditDTO dto) {
        Vip v = Vip.builder()
                .id(dto.getId())
                .vname(dto.getVname())
                .vphone(dto.getVphone())
                .vbalance(dto.getVbalance())
                .vaddress(dto.getVaddress())
                .vsex(dto.getVsex())
                .build();
        return this.baseMapper.updateById(v);
    }

    @Override
    public List<Vip> getCondition(VipConditionDTO dto) {
        QueryWrapper<Vip> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Vip> lambda = wrapper.lambda();
        if(!StrUtil.isBlank(dto.getVname()))
        {
            lambda.like(Vip::getVname,dto.getVname());
        }
        if(!StrUtil.isBlank(dto.getId()))
        {
            lambda.like(Vip::getId,dto.getId());
        }
        if(!StrUtil.isBlank(dto.getVphone()))
        {
            lambda.like(Vip::getVphone,dto.getVphone());
        }
        if(!StrUtil.isBlank(dto.getVsex()))
        {
            lambda.eq(Vip::getVsex,dto.getVsex());
        }

        List<Vip> vs = this.baseMapper.selectList(lambda);
        return  vs;
    }
}

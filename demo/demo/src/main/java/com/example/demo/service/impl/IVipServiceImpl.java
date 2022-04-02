package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ProviderMapper;
import com.example.demo.mapper.VipMapper;
import com.example.demo.model.dto.VipAddDTO;
import com.example.demo.model.dto.VipEditDTO;
import com.example.demo.model.entity.Provider;
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
}

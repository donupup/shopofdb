package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ProviderMapper;
import com.example.demo.model.dto.ProviderAddDTO;
import com.example.demo.model.dto.ProviderConditionDTO;
import com.example.demo.model.dto.ProviderEditDTO;
import com.example.demo.model.entity.Provider;
import com.example.demo.model.entity.Vip;
import com.example.demo.service.IProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements IProviderService {

    @Override
    public List<Provider> getList() {
        return baseMapper.selectList(null);
    }

    @Override
    public Provider executeAdd(ProviderAddDTO dto) {
        Provider p = Provider.builder()
                .paddress(dto.getPaddress())
                .pname(dto.getPname())
                .pphone(dto.getPphone())
                .plinkman(dto.getPlinkman()).build();
        this.baseMapper.insert(p);
        return p;
    }

    @Override
    public int executeDelete(String id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int executeEdit(ProviderEditDTO dto) {
        Provider p = Provider.builder()
                .id(dto.getId())
                .plinkman(dto.getPlinkman())
                .pphone(dto.getPphone())
                .pname(dto.getPname())
                .paddress(dto.getPaddress()).build();
        return this.baseMapper.updateById(p);
    }

    @Override
    public String getNameById(String id) {
        Provider pro = this.baseMapper.selectById(id);
        return pro.getPname();
    }

    @Override
    public List<Provider> getCondition(ProviderConditionDTO dto) {
        QueryWrapper<Provider> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Provider> lambda = wrapper.lambda();
        if(!StrUtil.isBlank(dto.getPname()))
        {
            lambda.like(Provider::getPname,dto.getPname());
        }
        if(!StrUtil.isBlank(dto.getPphone()))
        {
            lambda.eq(Provider::getPphone,dto.getPphone());
        }
        if(!StrUtil.isBlank(dto.getPaddress()))
        {
            lambda.like(Provider::getPaddress,dto.getPaddress());
        }

        List<Provider> ps = this.baseMapper.selectList(lambda);
        return  ps;
    }
}

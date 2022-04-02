package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ProviderMapper;
import com.example.demo.model.dto.ProviderAddDTO;
import com.example.demo.model.dto.ProviderEditDTO;
import com.example.demo.model.entity.Provider;
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
}

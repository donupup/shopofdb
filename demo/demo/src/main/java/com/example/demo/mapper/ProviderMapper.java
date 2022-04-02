package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.Provider;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderMapper extends BaseMapper<Provider> {
}

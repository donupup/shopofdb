package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.entity.Category;
import com.example.demo.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class ICategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<Category> getList() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public String getNameById(String id) {
        Category ca = this.baseMapper.selectById(id);
        return ca.getCname();
    }
}

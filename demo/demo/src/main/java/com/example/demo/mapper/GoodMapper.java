package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.entity.Good;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodMapper extends BaseMapper<Good> {
}

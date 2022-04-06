package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CartMapper;
import com.example.demo.model.entity.Cart;
import com.example.demo.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ICartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {
}

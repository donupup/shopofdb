package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.entity.Tip;

public interface ITipService extends IService<Tip> {
    Tip getRandomTip();
}

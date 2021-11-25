package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.model.dto.GoodAddDTO;
import com.example.demo.model.dto.GoodEditDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.User;
import com.example.demo.service.IGoodService;
import com.example.demo.utils.MD5Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IGoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

    @Resource
    private GoodMapper goodMapper;


    @Override
    public List<Good> getGoodList() {
        List<Good> goods = goodMapper.selectList(null);
        return goods;
    }

    @Override
    public int executeDelete(String id) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::getId, id);
        Good good_old = this.baseMapper.selectOne(wrapper);
        int toOut = outGood(good_old.getGoodname(),good_old.getStorage(),good_old.getId(),good_old.getBio(),1,new Date());
        int result = this.baseMapper.delete(wrapper);
        return result;
    }

    @Override
    public int executeEdit(GoodEditDTO dto) {
        Good good = new Good();
        good.setGoodname(dto.getGoodname());
        good.setPricein(dto.getPricein());
        good.setPricesell(dto.getPricesell());
        good.setStorage(dto.getStorage());
        good.setStatus(dto.isStatus());
        good.setModifyTime(new Date());
        good.setBio(dto.getBio());
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::getId,dto.getId());
        Good good_old = this.baseMapper.selectOne(wrapper);
        int change_num = good.getStorage() - good_old.getStorage();
        if(change_num < 0){
            int res = outGood(good.getGoodname(),Math.abs(change_num),good_old.getId(), dto.getBio(), 0,good.getModifyTime());
        }
        int res = inGood(good.getGoodname(),Math.abs(change_num),good_old.getId(), dto.getBio(),good.getModifyTime());
        int result = this.baseMapper.update(good,wrapper);
        return result;
    }

    @Override
    public Good executeAdd(GoodAddDTO dto) {
        Good good = Good.builder()
                        .shelflife(dto.getShelflife())
                        .goodname(dto.getGoodname())
                        .bio(dto.getBio())
                        .pricein(dto.getPricein())
                        .pricesell(dto.getPricesell())
                        .status(dto.isStatus())
                        .storage(dto.getStorage())
                        .createTime(new Date())
                        .modifyTime(new Date())
                        .build();
        this.baseMapper.insert(good);
        this.baseMapper.inGood(good.getGoodname(),good.getStorage(),good.getId(),good.getBio(),good.getModifyTime());
        return good;
    }

    @Override
    public int outGood(String name, int num, String good_id, String bio, int is_del, Date date) {
       return this.baseMapper.outGood(name,  num, good_id, bio,is_del,date);
    }

    @Override
    public int inGood(String name, int num, String good_id, String bio, Date date) {
        return this.baseMapper.inGood(name, num, good_id, bio, date);
    }
}

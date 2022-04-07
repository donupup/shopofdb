package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.ProviderMapper;
import com.example.demo.mapper.SaleGoodMapper;
import com.example.demo.model.dto.GoodAddDTO;
import com.example.demo.model.dto.GoodConditionDTO;
import com.example.demo.model.dto.GoodEditDTO;
import com.example.demo.model.dto.goodSaleDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.SaleGood;
import com.example.demo.model.entity.Vip;
import com.example.demo.model.vo.InGood;
import com.example.demo.model.vo.OutGood;
import com.example.demo.service.IGoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class IGoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

    @Resource
    private GoodMapper goodMapper;
    @Resource
    private SaleGoodMapper saleGoodMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProviderMapper providerMapper;

    public IGoodServiceImpl(SaleGoodMapper saleGoodMapper) {
        this.saleGoodMapper = saleGoodMapper;
    }


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
        //int toOut = outGood(good_old.getGoodname(),good_old.getStorage(),good_old.getId(),good_old.getBio(),1,new Date());
        int result = this.baseMapper.delete(wrapper);

        return result;
    }

    @Override
    public int executeEdit(GoodEditDTO dto) {
        System.out.println(dto);
        Good good = goodMapper.selectById(dto.getId());
        good.setGoodname(dto.getGoodname());
        //good.setPricein(dto.getPricein());
        //good.setPricesell(dto.getPricesell());
        //good.setStorage(dto.getStorage());
        good.setModifyTime(new Date());
        good.setBio(dto.getBio());
        good.setSpecification(dto.getSpecification());
        String catName = dto.getCategoryId();
        String proName = dto.getProviderId();
        if(!StrUtil.isBlank(dto.getCategoryId()))
        {
            good.setCategoryId(dto.getCategoryId());
        }
        if(!StrUtil.isBlank(dto.getProviderId()))
        {
            good.setProviderId(dto.getProviderId());
        }
//        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Good::getId,dto.getId());
        //Good good_old = this.baseMapper.selectOne(wrapper);
//        int change_num = good.getStorage() - good_old.getStorage();
//        if(change_num < 0){
//            if(good.getStorage() == 0)
//            {
//                int res = outGood(good.getGoodname(),Math.abs(change_num),good_old.getId(), dto.getBio(), 1,good.getModifyTime());
//            }
//            else{
//                int res = outGood(good.getGoodname(),Math.abs(change_num),good_old.getId(), dto.getBio(), 0,good.getModifyTime());
//            }
//
//        }
//        else{
//            int res = inGood(good.getGoodname(),Math.abs(change_num),good_old.getId(), dto.getBio(),good.getModifyTime(),dto.getPricein());
//        }
        int result = this.baseMapper.updateById(good);
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
                        .storage(dto.getStorage())
                        .createTime(new Date())
                        .modifyTime(new Date())
                .specification(dto.getSpecification())
                .categoryId(dto.getCategoryId())
                .ProviderId(dto.getProviderId())
                        .build();
        this.baseMapper.insert(good);
        //this.baseMapper.inGood(good.getGoodname(),good.getStorage(),good.getId(),good.getBio(),good.getModifyTime(),good.getPricein());
//        Category cat = this.categoryMapper.selectById(dto.getCategoryId());
//        Category c = Category.builder().cid(cat.getCid()).cname(cat.getCname()).availableNum(cat.getAvailableNum() + 1).build();
//        this.categoryMapper.updateById(c);
        return good;
    }

    @Override
    public List<Good> getCondition(GoodConditionDTO dto) {
        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Good> lambda = wrapper.lambda();
        if(!StrUtil.isBlank(dto.getGoodname()))
        {
            lambda.like(Good::getGoodname,dto.getGoodname());
        }
        if(!StrUtil.isBlank(dto.getCategoryId()))
        {
            lambda.eq(Good::getCategoryId,dto.getCategoryId());
        }
        if(!StrUtil.isBlank(dto.getProviderId()))
        {
            lambda.eq(Good::getProviderId,dto.getProviderId());
        }
        if(!StrUtil.isBlank(dto.getPricein()))
        {
            lambda.eq(Good::getPricein,dto.getPricein());
        }
        if(!StrUtil.isBlank(dto.getPricesell()))
        {
            lambda.eq(Good::getPricesell,dto.getPricesell());
        }
        if(!StrUtil.isBlank(dto.getStorage()))
        {
            int storage = Integer.parseInt(dto.getStorage());
            lambda.le(Good::getStorage,storage);
        }

        List<Good> gs = this.baseMapper.selectList(lambda);
        return  gs;
    }


    @Override
    public Good getById(String id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public int outGood(String name, int num, String good_id, String bio, int is_del, Date date) {
       return this.baseMapper.outGood(name,  num, good_id, bio,is_del,date);
    }

    @Override
    public int inGood(String name, int num, String good_id, String bio, Date date,int inPrice) {
        return this.baseMapper.inGood(name, num, good_id, bio, date,inPrice);
    }

    @Override
    public int sellGoodToTable(String name, int num, String good_id, int in, int out, Date date) {
        return baseMapper.sellGoodToTable(name, num, good_id, in, out, date);
    }

    @Override
    public List<Good> sellGood(List<goodSaleDTO> goodArray) {

        List<Good> goodsReturn = new ArrayList<>();
        for (goodSaleDTO good : goodArray) {
            LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Good::getGoodname, good.getGoodname());
            Good good_old = this.baseMapper.selectOne(wrapper);
            Good newGood =  Good.builder()
                    .shelflife(good_old.getShelflife())
                    .goodname(good_old.getGoodname())
                    .bio(good_old.getBio())
                    .pricein(good_old.getPricein())
                    .pricesell(good_old.getPricesell())
                    .storage(good_old.getStorage())
                    .createTime(new Date())
                    .modifyTime(new Date())
                    .build();
            if(good.getNum() > 0){
                newGood.setStorage(good_old.getStorage() - good.getNum());
                newGood.setSalenum(good_old.getSalenum() + good.getNum());
                int res = baseMapper.update(newGood, wrapper);
                if(res > 0) goodsReturn.add(newGood);
                int res2 = sellGoodToTable(good.getGoodname(),good.getNum(),good_old.getId(),(int)good_old.getPricein(),(int)good_old.getPricesell(),new Date());
            }
        }
        return goodsReturn;
    }

    @Override
    public Map<String, Integer> getSoldGood() {

        // LambdaQueryWrapper<Good> goodwrapper = new LambdaQueryWrapper<>();
        System.out.println(1);
        //System.out.println(baseMapper.selectList(goodwrapper));
        return null;
    }

    @Override
    public List<SaleGood> getSaleList() {
        List<SaleGood> soldGood  = saleGoodMapper.selectList(null);
        for (SaleGood good : soldGood
             ) {
            System.out.println(good);
        }
        return soldGood;
    }

    @Override
    public List<InGood> getInList() {
        List<InGood> l = new ArrayList<>();
        l = baseMapper.getInList();
        return l;
    }

    @Override
    public List<OutGood> getOutList() {
        List<OutGood> l = new ArrayList<>();
        l = baseMapper.getOutList();
        return  l;
    }

    @Override
    public List<Good> searchByKey(String key) {
        return this.baseMapper.searchByKey(key);
    }

    @Override
    public Good findByName(String name) {
        return baseMapper.findByName(name);
    }
}

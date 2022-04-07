package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.mapper.GoodSaleMapper;
import com.example.demo.mapper.VipMapper;
import com.example.demo.model.dto.GoodInEditDTO;
import com.example.demo.model.dto.GoodSaleConditionDTO;
import com.example.demo.model.dto.SaleADDDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodIn;
import com.example.demo.model.entity.GoodSale;
import com.example.demo.model.entity.Vip;
import com.example.demo.service.IGoodSaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IGoodSaleServiceImpl extends ServiceImpl<GoodSaleMapper, GoodSale> implements IGoodSaleService {

    @Autowired
    VipMapper vipMapper;
    @Autowired
    GoodMapper goodMapper;

    @Override
    public List<GoodSale> getlist() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public int executeEdit(GoodInEditDTO dto) {
        GoodSale gs = GoodSale.builder().id(dto.getId()).num(dto.getNum()).bio(dto.getBio()).build();
        int res = this.baseMapper.updateById(gs);
        return res;
    }

    @Override
    public int executeDelete(String id) {
        return  this.baseMapper.deleteById(id);
    }

    @Override
    public List<GoodSale> getConditionList(GoodSaleConditionDTO dto) {
        QueryWrapper<GoodSale> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> lambda = wrapper.lambda();
//        if(!StrUtil.isBlank(dto.getGoodid()))
//        {
//            lambda.eq(GoodSale::getGoodId,dto.getGoodid());
//        }
//        if(!StrUtil.isBlank(dto.getVipid()))
//        {
//            lambda.eq(GoodSale::getVipId,dto.getVipid());
//        }
//        if(!StrUtil.isBlank(dto.getUserid()))
//        {
//            lambda.eq(GoodSale::getUserId,dto.getUserid());
//        }
//        if(dto.getStarttime() != null)
//        {
//            lambda.ge(GoodSale::getGoodSoldTime,dto.getStarttime());
//        }
//        if(dto.getEndtime() != null)
//        {
//            lambda.le(GoodSale::getGoodSoldTime,dto.getEndtime());
//        }
        QueryWrapper<Vip> w = new QueryWrapper<>();
        LambdaQueryWrapper<Vip> l = w.lambda();
        if(!StrUtil.isBlank(dto.getGoodid()))
        {
            QueryWrapper<Good> wr = new QueryWrapper<>();
            LambdaQueryWrapper<Good> la = wr.lambda();
            la.like(Good::getGoodname,dto.getGoodid());
            List<Good> goods = goodMapper.selectList(la);
            if(goods.isEmpty()){
                lambda.eq(GoodSale::getGoodId,"");
            }
            else{
                for (Good g:goods
                ) {
                    lambda.eq(GoodSale::getGoodId,g.getId());
                }
            }
        }
        if(!StrUtil.isBlank(dto.getVipcard()))
        {
            lambda.eq(GoodSale::getVipId,dto.getVipcard());
        }
        if(!StrUtil.isBlank(dto.getVipid()))
        {
            l.like(Vip::getVname,dto.getVipid());
            List<Vip> vips = this.vipMapper.selectList(l);
            System.out.println(vips);
            if(vips.isEmpty())
            {
                lambda.eq(GoodSale::getVipId,"");
            }
            else{
                for (Vip v:vips
                ) {
                    lambda.eq(GoodSale::getVipId,v.getId());
                }
            }

        }
        if(!StrUtil.isBlank(dto.getUserid()))
        {
            lambda.eq(GoodSale::getUserId,dto.getUserid());
        }
        if(dto.getStarttime() != null)
        {
            lambda.ge(GoodSale::getGoodSoldTime,dto.getStarttime());
        }
        if(dto.getEndtime() != null)
        {
            lambda.le(GoodSale::getGoodSoldTime,dto.getEndtime());
        }
        List<GoodSale> gs = this.baseMapper.selectList(lambda);
        return  gs;
    }

    @Override
    public GoodSale executeAdd(SaleADDDTO dto) {
        GoodSale g = GoodSale.builder().goodId(dto.getGoodid()).vipId(dto.getVipid()).userId(dto.getUserid()).bio(dto.getBio()).num(dto.getNum())
                .goodSoldTime(new Date()).build();
        this.baseMapper.insert(g);
        return g;
    }
}

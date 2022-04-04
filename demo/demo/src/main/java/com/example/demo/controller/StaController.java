package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodIn;
import com.example.demo.model.entity.GoodSale;
import com.example.demo.model.entity.Vip;
import com.example.demo.model.vo.GoodInInfo;
import com.example.demo.model.vo.GoodSaleInfo;
import com.example.demo.service.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/sta")
public class StaController {
    @Resource
    IGoodInService goodInService;
    @Resource
    private IGoodService igoodService;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private IProviderService providerService;
    @Resource
    private IUserService userService;
    @Resource
    private IGoodSaleService goodSaleService;
    @Resource
    private IVipService vipService;


    @RequestMapping(value = "/month",method = RequestMethod.POST)
    ApiResult<Object> getMonthData(@Valid @RequestBody Date d)
    {
        System.out.println(d);
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<GoodSale> salew = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> salel = salew.lambda();
        Date next = DateUtil.offsetMonth(d,1);
        System.out.println(next);
        salel.ge(GoodSale::getGoodSoldTime,d);
        salel.lt(GoodSale::getGoodSoldTime,next);
        List<GoodSale> goodsale = this.goodSaleService.getBaseMapper().selectList(salel);
        QueryWrapper<GoodIn> inw = new QueryWrapper<>();
        LambdaQueryWrapper<GoodIn> inl = inw.lambda();
        inl.ge(GoodIn::getGoodInTime,d);
        inl.lt(GoodIn::getGoodInTime,next);
        List<GoodIn> goodin = this.goodInService.getBaseMapper().selectList(inl);
        int saleSum= 0;
        int salePrice = 0;
        for (GoodSale gs: goodsale
             ) {
            saleSum += gs.getNum();
            salePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
        }
        List<GoodSaleInfo> resInfo = new ArrayList<>();
        for (GoodSale gs:goodsale){
            String goodname = this.igoodService.getById(gs.getGoodId()).getGoodname();
            String username = this.userService.getUserById(gs.getUserId()).getUsername();
            String date = DateUtil.formatDateTime(gs.getGoodSoldTime());
            String vipname = this.vipService.getById(gs.getVipId()).getVname();
            int oneprice = this.igoodService.getById(gs.getGoodId()).getPricesell();
            int totalprice = gs.getNum() * oneprice;
            int inprice = this.igoodService.getById(gs.getGoodId()).getPricein();
            GoodSaleInfo gsi = GoodSaleInfo.builder().id(gs.getId()).num(gs.getNum()).oneprice(oneprice).totalprice(totalprice)
                    .goodname(goodname).vipname(vipname).vipid(gs.getVipId()).inprice(inprice).
                    username(username).goodsoldtime(date).bio(gs.getBio()).build();
            resInfo.add(gsi);
        }
        int inSum = 0;
        int inPrice = 0;
        for (GoodIn gi: goodin
             ) {
            inSum += gi.getNum();
            inPrice += gi.getNum() * igoodService.getById(gi.getGoodId()).getPricein();
        }
        List<GoodInInfo> res = new ArrayList<>();
        for (GoodIn g:goodin
        ) {
            String pname = this.providerService.getNameById(g.getProviderId());
            String username = this.userService.getUserById(g.getUserId()).getUsername();
            String goodname = this.igoodService.getById(g.getGoodId()).getGoodname();
            String date = DateUtil.formatDateTime(g.getGoodInTime());
            int goodprice = this.igoodService.getById(g.getGoodId()).getPricein();
            GoodInInfo gii =  GoodInInfo.builder().goodPrice(goodprice).providerName(pname).goodName(goodname).id(g.getId()).bio(g.getBio())
                    .num(g.getNum()).userName(username).goodInTime(date).build();
            res.add(gii);

        }
        int totalProfit = salePrice - inPrice;
        map.put("saleList",resInfo);
        map.put("inList",res);
        map.put("saleSum",saleSum);
        map.put("inSum",inSum);
        map.put("salePrice",salePrice);
        map.put("inPrice",inPrice);
        map.put("totalProfit",totalProfit);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/goodnum",method = RequestMethod.POST)
    ApiResult<Object> getGoodData(@Valid @RequestBody String gid){
        Good g = this.igoodService.getById(gid);
        QueryWrapper<GoodSale> salew = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> salel = salew.lambda();
        salel.eq(GoodSale::getGoodId,g.getId());
        int saleNum = this.goodSaleService.getBaseMapper().selectList(salel).size();

        QueryWrapper<GoodIn> inw = new QueryWrapper<>();
        LambdaQueryWrapper<GoodIn> inl = inw.lambda();
        inl.eq(GoodIn::getGoodId,g.getId());
        int inNum = this.goodInService.getBaseMapper().selectList(inl).size();

        Map<String,Object> map = new HashMap<>();
        map.put("saleNum",saleNum);
        map.put("inNum",inNum);
        return ApiResult.success(map);
    }

}

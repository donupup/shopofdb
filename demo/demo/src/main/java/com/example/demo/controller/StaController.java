package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.*;
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
        Date beginofyear = DateUtil.beginOfYear(d);
        List<Integer>  yearsaleNum = new ArrayList<>();
        List<Integer> yearsalePrice = new ArrayList<>();
        List<Integer> yearsaleProfit = new ArrayList<>();
        List<Integer> yearinNum = new ArrayList<>();
        List<Integer> yearinPrice = new ArrayList<>();
        List<Integer> yeartotalProfit = new ArrayList<>();
         for(int i = 0;i < 12;i ++)
        {
            Date perMonth = DateUtil.offsetMonth(beginofyear,i);
            Date pernext = DateUtil.offsetMonth(perMonth,1);

            QueryWrapper<GoodSale> salewra = new QueryWrapper<>();
            LambdaQueryWrapper<GoodSale> salelam = salewra.lambda();
            QueryWrapper<GoodIn> inwra = new QueryWrapper<>();
            LambdaQueryWrapper<GoodIn> inlam = inwra.lambda();

            salelam.ge(GoodSale::getGoodSoldTime,perMonth);
            salelam.lt(GoodSale::getGoodSoldTime,pernext);

            inlam.ge(GoodIn::getGoodInTime,perMonth);
            inlam.lt(GoodIn::getGoodInTime,pernext);

            List<GoodSale> goodsale = this.goodSaleService.getBaseMapper().selectList(salelam);
            List<GoodIn> goodin = this.goodInService.getBaseMapper().selectList(inlam);

            int saleSum= 0;
            int salePrice = 0;
            int saleProfit = 0;
            for (GoodSale gs: goodsale
            ) {
                saleSum += gs.getNum();
                salePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
                saleProfit += gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein());
                Good g1 = igoodService.getById(gs.getGoodId());
            }
            yearsaleNum.add(saleSum);
            yearsalePrice.add(salePrice);
            yearsaleProfit.add(saleProfit);

            int inSum = 0;
            int inPrice = 0;
            for (GoodIn gi: goodin
            ) {
                inSum += gi.getNum();
                inPrice += gi.getNum() * igoodService.getById(gi.getGoodId()).getPricein();
            }
            yearinNum.add(inSum);
            yearinPrice.add(inPrice);

            yeartotalProfit.add(saleProfit - inPrice);
        }


        Map<String,Object> goodsalePrice = new HashMap<>();
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
        int saleProfit = 0;
        for (GoodSale gs: goodsale
             ) {
            saleSum += gs.getNum();
            salePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
            saleProfit += gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein());
            Good g1 = igoodService.getById(gs.getGoodId());
            if(!goodsalePrice.containsKey(g1.getGoodname()))
            {
                goodsalePrice.put(g1.getGoodname(),g1.getPricesell() * gs.getNum());
            }
            else{
                Object value = goodsalePrice.get(g1.getGoodname());
                goodsalePrice.put(g1.getGoodname(),(int)value + (g1.getPricesell() * gs.getNum()));
            }
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
        int totalProfit = saleProfit - inPrice;
        map.put("saleList",resInfo);
        map.put("inList",res);
        map.put("saleSum",saleSum);
        map.put("inSum",inSum);
        map.put("salePrice",salePrice);
        map.put("inPrice",inPrice);
        map.put("saleProfit",saleProfit);
        map.put("totalProfit",totalProfit);
        map.put("goodsalePrice",goodsalePrice);
        map.put("yearsaleNum",yearsaleNum);
        map.put("yearsalePrice",yearsalePrice);
        map.put("yearsaleProfit",yearsaleProfit);
        map.put("yearinNum",yearinNum);
        map.put("yearinPrice",yearinPrice);
        map.put("yeartotalProfit",yeartotalProfit);



        return ApiResult.success(map);
    }

    @RequestMapping(value = "/goodnum",method = RequestMethod.POST)
    ApiResult<Object> getGoodData(@Valid @RequestBody String gid){
        String[] arr = gid.split("=");
        gid = arr[0];
        Good g = this.igoodService.getById(gid);
        if(ObjectUtils.isEmpty(g))
        {
            return ApiResult.failed("没有该商品");
        }
        QueryWrapper<GoodSale> salew = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> salel = salew.lambda();
        salel.eq(GoodSale::getGoodId,g.getId());


        //这是销售记录的条数
        int saleNum = this.goodSaleService.getBaseMapper().selectList(salel).size();
        List<GoodSale> goodsale = this.goodSaleService.getBaseMapper().selectList(salel);

        int  totalSaleNum = 0; //总件数
        int totalSalePrice = 0; //总售价
        int totalSaleProfit = 0; //总销售利润
        for (GoodSale gs:goodsale
             ) {
            totalSaleNum += gs.getNum();
            totalSalePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
            totalSaleProfit += gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein());
        }
        //System.out.println(saleNum);

        QueryWrapper<GoodIn> inw = new QueryWrapper<>();
        LambdaQueryWrapper<GoodIn> inl = inw.lambda();
        inl.eq(GoodIn::getGoodId,g.getId());

        //这是进货记录的条数
        int inNum = this.goodInService.getBaseMapper().selectList(inl).size();
        List<GoodIn> goodin = this.goodInService.getBaseMapper().selectList(inl);
        int totalInNum = 0;
        int totalInPrice = 0;
        for (GoodIn gi:goodin
             ) {
            totalInNum += gi.getNum();
            totalInPrice += gi.getNum() * this.igoodService.getById(gi.getGoodId()).getPricein();
        }
        //System.out.println(inNum);
        Map<String,Object> map = new HashMap<>();
        map.put("saleNum",saleNum);
        map.put("inNum",inNum);
        map.put("totalSaleNum",totalSaleNum);
        map.put("totalSalePrice",totalSalePrice);
        map.put("totalSaleProfit",totalSaleProfit);
        map.put("totalInNum",totalInNum);
        map.put("totalInPrice",totalInPrice);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/good",method = RequestMethod.POST)
    ApiResult<Object> getGoodSta(){

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> goodsaleMap = new HashMap<>();
        Map<String,Object> goodsaleprice = new HashMap<>();
        Map<String,Object> goodstorageMap = new HashMap<>();
        Map<String,Object> catsaleMap = new HashMap<>();
        Map<String,Object> catsaleprice = new HashMap<>();
        List<GoodSale> goodsale = goodSaleService.getBaseMapper().selectList(null);
        for (GoodSale gs:goodsale
             ) {
            Good g = igoodService.getById(gs.getGoodId());
            if(!goodsaleMap.containsKey(g.getGoodname()))
            {
                goodsaleMap.put(g.getGoodname(),gs.getNum());
                goodsaleprice.put(g.getGoodname(),gs.getNum() * g.getPricesell());
            }
            else
            {
                Object value = goodsaleMap.get(g.getGoodname());
                Object value1 = goodsaleprice.get(g.getGoodname());
                goodsaleMap.put(g.getGoodname(),gs.getNum() + (int)value);
                goodsaleprice.put(g.getGoodname(),(int)value1 + (gs.getNum() * g.getPricesell()));
            }
            Category cat = categoryService.getById(g.getCategoryId());
            if(!catsaleMap.containsKey(cat.getCname()))
            {
                catsaleMap.put(cat.getCname(),gs.getNum());
                catsaleprice.put(cat.getCname(),gs.getNum() *  g.getPricesell());
            }
            else{
                Object value2 = catsaleMap.get(cat.getCname());
                Object value3 = catsaleprice.get(cat.getCname());
                catsaleMap.put(cat.getCname(),(int)value2 + gs.getNum());
                catsaleprice.put(cat.getCname(),(int)value3 + (gs.getNum() * g.getPricesell()));
            }
        }
        map.put("goodsale",goodsaleMap);
        Map<String,Object> categoryStorageMap = new HashMap<>();
        List<Good>  goods = igoodService.getBaseMapper().selectList(null);
        for (Good g:goods
             ) {
                goodstorageMap.put(g.getGoodname(),g.getStorage());
            Category cat = categoryService.getById(g.getCategoryId());
            if(!categoryStorageMap.containsKey(cat.getCname()))
            {
                categoryStorageMap.put(cat.getCname(), g.getStorage());
            }
            else{
                Object value = categoryStorageMap.get(cat.getCname());
                categoryStorageMap.put(cat.getCname(),(int)value + g.getStorage());
            }
        }
//        System.out.println(categoryStorageMap);
//        System.out.println(goodstorageMap);
        map.put("categorystorage",categoryStorageMap);
        map.put("goodstorage",goodstorageMap);
        map.put("goodsalePrice",goodsaleprice);
        map.put("catsalePrice",catsaleprice);
        map.put("catsale",catsaleMap);

        return ApiResult.success(map);
    }

}

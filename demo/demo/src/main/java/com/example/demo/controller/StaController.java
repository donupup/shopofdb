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
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.Oneway;
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
        int maleSale = 0;
        int femaleSale = 0;
        for (GoodSale gs:goodsale
             ) {
            totalSaleNum += gs.getNum();
            totalSalePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
            totalSaleProfit += gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein());
            Vip v = vipService.getById(gs.getVipId());
            if(v.getVsex().equals("男"))
            {
                maleSale += gs.getNum();
            }
            else{
                femaleSale += gs.getNum();
            }
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
        map.put("goodInfo",g);
        map.put("maleSale",maleSale);
        map.put("femaleSale",femaleSale);
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

    //todo: 该用户购买的商品种类的柱图
    @RequestMapping(value = "/numofvip",method = RequestMethod.POST)
    ApiResult<Object> getNumOfVip(@Valid @RequestBody String vid)
    {
        String[] arr = vid.split("=");
        vid = arr[0];
        System.out.println(vid);
        Vip v = vipService.getBaseMapper().selectById(vid);
        if(ObjectUtils.isEmpty(v))
        {
            return  ApiResult.failed("没有对应的会员");
        }
        QueryWrapper<GoodSale> salewra = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> salelam = salewra.lambda();
        salelam.eq(GoodSale::getVipId,vid);
        List<GoodSale> goodsale = goodSaleService.getBaseMapper().selectList(salelam);

        List<Category> categories = categoryService.getBaseMapper().selectList(null);
        List<String> cnames = new ArrayList<>();
        for (Category cat:categories
             ) {
            cnames.add(cat.getCname());
        }
        Map<String,Object> catSaleNum = new HashMap<>();
        Map<String,Object> catSalePrice = new HashMap<>();
        int buyNum = 0;
        int buyPrice = 0;
        int buyTimes = goodsale.size();
        for (GoodSale gs:goodsale
             ) {
            buyNum += gs.getNum();
            buyPrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
            Category c = categoryService.getById(igoodService.getById(gs.getGoodId()).getCategoryId());
            if(!catSaleNum.containsKey(c.getCname()))
            {
                catSaleNum.put(c.getCname(),gs.getNum());
                catSalePrice.put(c.getCname(),gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell());
            }
            else
            {
                Object val1 = catSaleNum.get(c.getCname());
                catSaleNum.put(c.getCname(),(int)val1 + gs.getNum());

                Object val2 = catSalePrice.get(c.getCname());
                catSalePrice.put(c.getCname(),(int)val2 + gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell());
            }
        }
        List<Object> catNum = new ArrayList<>();
        List<Object> catPrice = new ArrayList<>();
        for (String s: cnames
             ) {
            if(catSaleNum.containsKey(s))
            {
                catNum.add(catSaleNum.get(s));
                catPrice.add(catSalePrice.get(s));
            }
            else{
                catNum.add(0);
                catPrice.add(0);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("buyNum",buyNum);
        map.put("buyPrice",buyPrice);
        map.put("buyTimes",buyTimes);
        map.put("vip",v);
        map.put("cnames",cnames);
        map.put("catNum",catNum);
        map.put("catPrice",catPrice);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/vip",method = RequestMethod.POST)
    ApiResult<Object> getVipSta()
    {
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Vip> vipw1 = new QueryWrapper<>();
        LambdaQueryWrapper<Vip> vipl1 = vipw1.lambda();
        vipl1.eq(Vip::getVsex,"男");
        List<Vip> maleVip = vipService.getBaseMapper().selectList(vipl1);
        //System.out.println(maleVip);
        int maleNum = maleVip.size();
        //System.out.println(maleNum);
        QueryWrapper<Vip> vipw2 = new QueryWrapper<>();
        LambdaQueryWrapper<Vip> vipl2 = vipw2.lambda();
        vipl2.eq(Vip::getVsex,"女");
        List<Vip> femaleVip = vipService.getBaseMapper().selectList(vipl2);
        //System.out.println(femaleVip);
        int femaleNum = femaleVip.size();
        //System.out.println(femaleNum);
        Map<String,Object> vsexNum = new HashMap<>();
        vsexNum.put("男会员",maleNum);
        vsexNum.put("女会员",femaleNum);


        Map<Object,Object> agemap = new HashMap<>();
        for(int i = 10;i < 81; i = i + 10)
        {
            QueryWrapper<Vip> vipw3 = new QueryWrapper<>();
            LambdaQueryWrapper<Vip> vipl3 = vipw3.lambda();
            vipl3.ge(Vip::getVage,i);
            vipl3.lt(Vip::getVage,i + 10);
            List<Vip> vipage = vipService.getBaseMapper().selectList(vipl3);
            int len = vipage.size();
            agemap.put(String.valueOf(i) + "-" + String.valueOf(i + 9),len);
        }
        map.put("agemap",agemap);

        int maleSaleNum = 0;
        int femaleSaleNum = 0;
        int maleSalePrice = 0;
        int femaleSalePrice = 0;
        List<GoodSale> goodsale = goodSaleService.getBaseMapper().selectList(null);
        for (GoodSale gs:goodsale
             ) {
            String vid = gs.getVipId();
            Vip vgs = vipService.getById(vid);
            Good g = igoodService.getById(gs.getGoodId());
            if(vgs.getVsex().equals("男")){
                maleSaleNum += gs.getNum();
                maleSalePrice += gs.getNum() * g.getPricesell();
            }
            else{
                femaleSaleNum += gs.getNum();
                femaleSalePrice += gs.getNum() * g.getPricesell();
            }
        }
        Map<String,Object> vsexSaleNum = new HashMap<>();
        vsexSaleNum.put("男会员",maleSaleNum);
        vsexSaleNum.put("女会员",femaleSaleNum);
        Map<String,Object> vsexSalePrice = new HashMap<>();
        vsexSalePrice.put("男会员",maleSalePrice);
        vsexSalePrice.put("女会员",femaleSalePrice);

        map.put("vsexNum",vsexNum);
        map.put("vsexSaleNum",vsexSaleNum);
        map.put("vsexSalePrice",vsexSalePrice);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/day",method = RequestMethod.POST)
    ApiResult<Object> getDaySta(@Valid @RequestBody Date d)
    {
        System.out.println(d);
        Date next = DateUtil.offsetDay(d,1);
        int saleNum = 0;
        int salePrice = 0;
        int inNum = 0;
        int inPrice = 0;
        int saleProfit = 0;
        int totalProfit = 0;
        QueryWrapper<GoodSale> salewra = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> salelam = salewra.lambda();
        QueryWrapper<GoodIn> inwra = new QueryWrapper<>();
        LambdaQueryWrapper<GoodIn> inlam = inwra.lambda();

        salelam.ge(GoodSale::getGoodSoldTime,d);
        salelam.lt(GoodSale::getGoodSoldTime,next);

        inlam.ge(GoodIn::getGoodInTime,d);
        inlam.lt(GoodIn::getGoodInTime,next);

        List<GoodSale> goodsale = this.goodSaleService.getBaseMapper().selectList(salelam);
        List<GoodIn> goodin = this.goodInService.getBaseMapper().selectList(inlam);

        List<Category> categories = categoryService.getBaseMapper().selectList(null);
        List<String> cname = new ArrayList<>();
        for (Category ca:categories
             ) {
            cname.add(ca.getCname());
        }
        Map<String,Object> catsalenummap = new HashMap<>();
        Map<String,Object> catsalepricemap = new HashMap<>();
        Map<String,Object> catsaleprofit = new HashMap<>();

        Map<String,Object> map = new HashMap<>();

        for (GoodSale gs: goodsale
        ) {
            Good g = igoodService.getById(gs.getGoodId());
            Category cat = categoryService.getBaseMapper().selectById(g.getCategoryId());
            saleNum += gs.getNum();
            salePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
            saleProfit += gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein());

            if(!catsalenummap.containsKey(cat.getCname()))
            {
                catsalenummap.put(cat.getCname(),gs.getNum());
                catsalepricemap.put(cat.getCname(),gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell());
                catsaleprofit.put(cat.getCname(),gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein()));
            }
            else
            {
                Object value1 = catsalenummap.get(cat.getCname());
                Object value2 = catsalepricemap.get(cat.getCname());
                Object value3 = catsaleprofit.get(cat.getCname());
                catsalenummap.put(cat.getCname(),(int)value1 + gs.getNum());
                catsalepricemap.put(cat.getCname(),(int)value2 + gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell());
                catsaleprofit.put(cat.getCname(),(int)value3 + gs.getNum() * (igoodService.getById(gs.getGoodId()).getPricesell() - igoodService.getById(gs.getGoodId()).getPricein()));
            }
        }
        List<Integer> catsaleNum = new ArrayList<>();
        List<Integer> catsalePrice = new ArrayList<>();
        List<Integer> catsaleProfit = new ArrayList<>();
        for (Category c:categories
             ) {
            if(catsalenummap.containsKey(c.getCname()))
            {
                catsaleNum.add((int)catsalenummap.get(c.getCname()));
                catsalePrice.add((int)catsalepricemap.get(c.getCname()));
                catsaleProfit.add((int)catsaleprofit.get(c.getCname()));
            }
            else{
                catsaleNum.add(0);
                catsalePrice.add(0);
                catsaleProfit.add(0);
            }

        }
        map.put("catsaleNum",catsaleNum);
        map.put("catsalePrice",catsalePrice);
        map.put("catsaleProfit",catsaleProfit);
        for (GoodIn gi: goodin
        ) {
            inNum += gi.getNum();
            inPrice += gi.getNum() * igoodService.getById(gi.getGoodId()).getPricein();
        }
        totalProfit = saleProfit - inPrice;
        map.put("saleNum",saleNum);
        map.put("inNum",inNum);
        map.put("salePrice",salePrice);
        map.put("inPrice",inPrice);
        map.put("saleProfit",saleProfit);
        map.put("totalProfit",totalProfit);
        map.put("cnames",cname);


        return ApiResult.success(map);
    }

    @RequestMapping(value = "/numofuser",method = RequestMethod.POST)
    ApiResult<Object> getNumofUser(@Valid @RequestBody String uname)
    {
        String[] arr = uname.split("=");
        uname = arr[0];
        //System.out.println(uname);
        User user = userService.getUserByUsername(uname);
        if(ObjectUtils.isEmpty(user))
        {
            return ApiResult.failed("未搜索到该用户");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);

        QueryWrapper<GoodSale> salewra = new QueryWrapper<>();
        LambdaQueryWrapper<GoodSale> salelam = salewra.lambda();
        salelam.eq(GoodSale::getUserId,user.getId());
        List<GoodSale> goodsale = goodSaleService.getBaseMapper().selectList(salelam);

        QueryWrapper<GoodIn> salein = new QueryWrapper<>();
        LambdaQueryWrapper<GoodIn> salelin = salein.lambda();
        salelin.eq(GoodIn::getUserId,user.getId());
        List<GoodIn> goodin = goodInService.getBaseMapper().selectList(salelin);
        int saleNum = 0;
        int salePrice = 0;
        int inNum = 0;
        int inPrice = 0;
        for (GoodSale gs:goodsale
             ) {
            saleNum += gs.getNum();
            salePrice += gs.getNum() * igoodService.getById(gs.getGoodId()).getPricesell();
        }
        for (GoodIn gi: goodin
             ) {
            inNum += gi.getNum();
            inPrice += gi.getNum() * igoodService.getById(gi.getGoodId()).getPricein();
        }
        map.put("saleNum",saleNum);
        map.put("salePrice",salePrice);
        map.put("inNum",inNum);
        map.put("inPrice",inPrice);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    ApiResult<Object> getUserSta()
    {
        return ApiResult.success();
    }


}

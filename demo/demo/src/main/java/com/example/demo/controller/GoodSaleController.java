package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goodsale")
public class GoodSaleController {
    @Resource
    IGoodSaleService goodSaleService;
    @Resource
    private IGoodService igoodService;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private IProviderService providerService;
    @Resource
    private IUserService userService;
    @Resource
    private IVipService vipService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
//    public ApiResult<List<GoodSaleInfo>> getSaleList() {
//        System.out.println(123);
//        List<GoodSale> l = goodSaleService.getlist();
//        List<GoodSaleInfo> res = new ArrayList<>();
//        for (GoodSale gs:l){
//            String goodname = this.igoodService.getById(gs.getGoodId()).getGoodname();
//            String username = this.userService.getUserById(gs.getUserId()).getUsername();
//            String date = DateUtil.formatDateTime(gs.getGoodSoldTime());
//            String vipname = vipService.getById(gs.getVipId()).getVname();
//            int oneprice = this.igoodService.getById(gs.getGoodId()).getPricesell();
//            int totalprice = gs.getNum() * oneprice;
//            GoodSaleInfo gsi = GoodSaleInfo.builder().id(gs.getId()).num(gs.getNum()).oneprice(oneprice).totalprice(totalprice)
//                    .goodname(goodname).vipname(vipname).vipid(gs.getVipId()).username(username).goodsoldtime(date).bio(gs.getBio()).build();
//            res.add(gsi);
//        }
//        return ApiResult.success(res);
//    }
    public ApiResult<List<GoodSaleInfo>> getSaleList()
    {
        System.out.println(123);
        List<GoodSale> l = goodSaleService.getlist();
        List<GoodSaleInfo> res = new ArrayList<>();
        for (GoodSale gs:l){
            String goodname = this.igoodService.getById(gs.getGoodId()).getGoodname();
            String username = this.userService.getUserById(gs.getUserId()).getUsername();
            String date = DateUtil.formatDateTime(gs.getGoodSoldTime());
            String vipname = this.vipService.getById(gs.getVipId()).getVname();
            int oneprice = this.igoodService.getById(gs.getGoodId()).getPricesell();
            int totalprice = gs.getNum() * oneprice;
            GoodSaleInfo gsi = GoodSaleInfo.builder().id(gs.getId()).num(gs.getNum()).oneprice(oneprice).totalprice(totalprice)
                    .goodname(goodname).vipname(vipname).vipid(gs.getVipId()).username(username).goodsoldtime(date).bio(gs.getBio()).build();
            res.add(gsi);
     }
        return ApiResult.success(res);

    }


    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ApiResult<Object> editSale(@Valid @RequestBody GoodInEditDTO dto) {

        Good g = this.igoodService.getById(this.goodSaleService.getById(dto.getId()).getGoodId());
        int oldnum = this.goodSaleService.getById(dto.getId()).getNum();
        int storage = g.getStorage();
        storage = storage + oldnum - dto.getNum();
        g.setStorage(storage);
        this.igoodService.updateById(g);
        int result  = goodSaleService.executeEdit(dto);
        if(result > 0){
            return ApiResult.success(null,"编辑成功");
        }
        return ApiResult.failed("编辑失败");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteSale(@Valid @RequestBody GoodSaleInfo gi) {
        Good g = this.igoodService.getById(this.goodSaleService.getById(gi.getId()).getGoodId());
        g.setStorage(g.getStorage() + gi.getNum());
        this.igoodService.updateById(g);
        String id = gi.getId();
        System.out.println(id);
        int res = goodSaleService.executeDelete(id);
        if(res > 0) {
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    public ApiResult<List<GoodSaleInfo>> getConditionSale(@Valid @RequestBody GoodSaleConditionDTO dto){

        List<GoodSale> l = goodSaleService.getConditionList(dto);
        List<GoodSaleInfo> res = new ArrayList<>();
        for (GoodSale gs:l){
            String goodname = this.igoodService.getById(gs.getGoodId()).getGoodname();
            String username = this.userService.getUserById(gs.getUserId()).getUsername();
            String date = DateUtil.formatDateTime(gs.getGoodSoldTime());
            String vipname = this.vipService.getById(gs.getVipId()).getVname();
            int oneprice = this.igoodService.getById(gs.getGoodId()).getPricesell();
            int totalprice = gs.getNum() * oneprice;
            GoodSaleInfo gsi = GoodSaleInfo.builder().id(gs.getId()).num(gs.getNum()).oneprice(oneprice).totalprice(totalprice)
                    .goodname(goodname).vipname(vipname).vipid(gs.getVipId()).username(username).goodsoldtime(date).bio(gs.getBio()).build();
            res.add(gsi);
        }
        return ApiResult.success(res);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public  ApiResult<Object> addSale(@Valid @RequestBody SaleADDDTO dto){

        String vid = dto.getVipid();
        Vip v = vipService.getBaseMapper().selectById(vid);
        if (ObjectUtils.isEmpty(v)) {
            return ApiResult.failed("添加失败,请输入正确的ID");
        }
        GoodSale g = goodSaleService.executeAdd(dto);
        Good good = igoodService.getById(dto.getGoodid());
        int old_storage = good.getStorage();
        good.setStorage(old_storage - dto.getNum());
        igoodService.updateById(good);
        if (ObjectUtils.isEmpty(g)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("goodSale", g);
        return ApiResult.success(map);

    }
}

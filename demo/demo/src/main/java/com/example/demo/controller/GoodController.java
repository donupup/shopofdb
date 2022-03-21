package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.GoodAddDTO;
import com.example.demo.model.dto.GoodEditDTO;
import com.example.demo.model.dto.goodSaleDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.SaleGood;
import com.example.demo.model.entity.User;
import com.example.demo.model.vo.InGood;
import com.example.demo.model.vo.OutGood;
import com.example.demo.service.IGoodService;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/good")
public class GoodController extends  BaseController{

    @Resource
    private IGoodService igoodService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ApiResult<List<Good>> getGood() {
        List<Good> goods = igoodService.getGoodList();
        return ApiResult.success(goods);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteGood(@Valid @RequestBody String id){
        String[] arr = id.split("=");
        id = arr[0];
        int result = igoodService.executeDelete(id);
        if(result > 0) {
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ApiResult<Object> editGood(@Valid @RequestBody GoodEditDTO dto){

        int result = igoodService.executeEdit(dto);
        if(result > 0){
            return ApiResult.success(null,"编辑成功");
        }
        return ApiResult.failed("编辑失败");
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public  ApiResult<Object> addGood(@Valid @RequestBody GoodAddDTO dto){
        Good good = igoodService.executeAdd(dto);
        if (ObjectUtils.isEmpty(good)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("good", good);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/sale",method = RequestMethod.POST)
    public  ApiResult<Object> sellGood(@Valid  @RequestParam  Map<String,String> map){
        System.out.println(map);
        String goodMap = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            goodMap = entry.getKey();
            System.out.println(goodMap);
        }
        goodMap = goodMap.substring(1,goodMap.length()-1);
        String[] goods = goodMap.split("},");
        List<String> jsonArray = new ArrayList<>();
        for (int i = 0; i < goods.length; i++) {
            if(i == goods.length - 1)
            {
                System.out.println(goods[i]);
                jsonArray.add(goods[i]);
            }
            else
            {
                System.out.println(goods[i] + '}');
                jsonArray.add(goods[i] + '}');
            }
        }
        List<goodSaleDTO> goodArray = new ArrayList<>();
        for (String json : jsonArray
             ) {
            goodSaleDTO oneGoodSale = JSON.parseObject(json,goodSaleDTO.class);
            goodArray.add(oneGoodSale);
        }
//        for (goodSaleDTO good: goodArray
//             ) {
//            System.out.println(good.getGoodname());
//        }
        List<Good> goodsReturn = igoodService.sellGood(goodArray);
        if(ObjectUtils.isEmpty(goodsReturn))
            return ApiResult.failed("系统故障，卖出失败");
        return ApiResult.success("成功卖出");
    }

    //这里用来进行统计
    @RequestMapping(value = "/statistic/getsaledgood",method = RequestMethod.POST)
    public  ApiResult<Map<String, Integer>> getSoldGood(){
        igoodService.getSoldGood();
        return null;
    }

    @RequestMapping(value = "/salelist",method = RequestMethod.GET)
    public ApiResult<List<SaleGood>> getSaleList() {
        List<SaleGood> salegoods = igoodService.getSaleList();
        return ApiResult.success(salegoods);
    }

    @RequestMapping(value = "/inlist",method = RequestMethod.GET)
    public ApiResult<List<InGood>> getInList() {
        List<InGood> in = igoodService.getInList();
        return ApiResult.success(in);
    }

    @RequestMapping(value = "/outlist",method = RequestMethod.GET)
    public ApiResult<List<OutGood>> getOutList() {
        List<OutGood> out = igoodService.getOutList();
        return ApiResult.success(out);
    }




}


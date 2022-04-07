package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.*;
import com.example.demo.model.vo.GoodInfo;
import com.example.demo.model.vo.InGood;
import com.example.demo.model.vo.OutGood;
import com.example.demo.service.ICategoryService;
import com.example.demo.service.IGoodService;
import com.example.demo.service.IProviderService;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/good")
public class GoodController extends  BaseController{

    @Resource
    private IGoodService igoodService;
    @Resource
    private ICategoryService categoryService;
    @Resource
    private IProviderService providerService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ApiResult<List<GoodInfo>> getGood() {
        List<Good> goods = igoodService.getGoodList();
        List<GoodInfo> goodInfo = new ArrayList<>();
        for (Good g:goods
             ) {
            String pname = providerService.getNameById(g.getProviderId());
            String cname = categoryService.getNameById(g.getCategoryId());
            GoodInfo gi = GoodInfo.builder().goodname(g.getGoodname())
                    .id(g.getId())
                    .categoryName(cname)
                    .pricein(g.getPricein())
                    .pricesell(g.getPricesell())
                    .salenum(g.getSalenum())
                    .storage(g.getStorage())
                    .shelflife(g.getShelflife())
                    .bio(g.getBio())
                    .createTime(g.getCreateTime())
                    .modifyTime(g.getModifyTime())
                    .ProviderNmae(pname).specification(g.getSpecification()).build();
            goodInfo.add(gi);
        }
        return ApiResult.success(goodInfo);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteGood(@Valid @RequestBody String id){
        System.out.println(id);
        String[] arr = id.split("=");
        id = arr[0];
        System.out.println(id);
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

        String goodname = dto.getGoodname();
        QueryWrapper<Good> goodw = new QueryWrapper<>();
        LambdaQueryWrapper<Good> goodl = goodw.lambda();
        goodl.eq(Good::getGoodname,goodname);
        List<Good> goodwithname = igoodService.getBaseMapper().selectList(goodl);
        if(!goodwithname.isEmpty())
        {
            return ApiResult.failed("商品名已存在，请在名称后加规格表示区分~");
        }
        Good good = igoodService.executeAdd(dto);
        if (ObjectUtils.isEmpty(good)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("good", good);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    public ApiResult<List<GoodInfo>> getConditionUser(@Valid @RequestBody GoodConditionDTO dto) {
        System.out.println(dto);
        List<Good> l =  igoodService.getCondition(dto);
        List<GoodInfo> goodInfo = new ArrayList<>();
        for (Good g:l
        ) {
            String pname = providerService.getNameById(g.getProviderId());
            String cname = categoryService.getNameById(g.getCategoryId());
            GoodInfo gi = GoodInfo.builder().goodname(g.getGoodname())
                    .id(g.getId())
                    .categoryName(cname)
                    .pricein(g.getPricein())
                    .pricesell(g.getPricesell())
                    .salenum(g.getSalenum())
                    .storage(g.getStorage())
                    .shelflife(g.getShelflife())
                    .bio(g.getBio())
                    .createTime(g.getCreateTime())
                    .modifyTime(g.getModifyTime())
                    .ProviderNmae(pname).specification(g.getSpecification()).build();
            goodInfo.add(gi);
        }
        return ApiResult.success(goodInfo);
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

    @RequestMapping(value = "/file_good",method = RequestMethod.POST)
    public ApiResult<String> editGoodByFile(@Valid  @RequestBody JSONArray ja) throws ParseException {
        System.out.println(ja);
        for(int i = 0;i < ja.size();i++) {
//            System.out.println(ja.getJSONObject(i));
//            System.out.println(ja.getJSONObject(i).get("商品名称"));
            JSONObject good = ja.getJSONObject(i);
            Good g = igoodService.findByName(good.get("商品名称").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date d = sdf.parse(good.get("日期").toString());
            igoodService.inGood(good.get("商品名称").toString(),Integer.parseInt(good.get("数量").toString()),g.getId(),good.get("备注").toString(),d,Integer.parseInt(good.get("进价").toString()));
        }
        return ApiResult.success("成功进货");
    }
}


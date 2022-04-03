package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.GoodInEditDTO;
import com.example.demo.model.dto.VipAddDTO;
import com.example.demo.model.dto.VipConditionDTO;
import com.example.demo.model.dto.VipEditDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodIn;
import com.example.demo.model.entity.Vip;
import com.example.demo.model.vo.GoodInInfo;
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
@RequestMapping("/inport")
public class GoodInController {
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


    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ApiResult<List<GoodInInfo>> getInportList() {
//        List<Vip> list = vipService.getList();
//        System.out.println(list);
//        return ApiResult.success(list);
        List<GoodIn> l = goodInService.getlist();
        List<GoodInInfo> res = new ArrayList<>();
        for (GoodIn g:l
             ) {
            String pname = this.providerService.getNameById(g.getProviderId());
            String username = this.userService.getUserById(g.getUserId()).getUsername();
            String goodname = this.igoodService.getById(g.getGoodId()).getGoodname();
            int goodprice = this.igoodService.getById(g.getGoodId()).getPricein();
            GoodInInfo gii =  GoodInInfo.builder().goodPrice(goodprice).providerName(pname).goodName(goodname).id(g.getId()).bio(g.getBio())
                    .num(g.getNum()).userName(username).goodInTime(g.getGoodInTime()).build();
            res.add(gii);

        }
        return ApiResult.success(res);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteInport(@Valid @RequestBody GoodInInfo gi) {

        String id = gi.getId();
        int result = goodInService.executeDelete(id);
        if(result > 0) {
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");

    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
   public ApiResult<Object> editInport(@Valid @RequestBody GoodInEditDTO dto) {

        int result = i=goodInService.executeEdit(dto);
        if(result > 0){
            return ApiResult.success(null,"编辑成功");
        }
        return ApiResult.failed("编辑失败");
    }
//    @RequestMapping(value = "/add",method = RequestMethod.POST)
//    public  ApiResult<Object> addInport(@Valid @RequestBody VipAddDTO dto){
//
//        Vip p = vipService.executeAdd(dto);
//        if (ObjectUtils.isEmpty(p)) {
//            return ApiResult.failed("添加失败");
//        }
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("vip", p);
//        return ApiResult.success(map);
//    }
//
//    @RequestMapping(value = "/delete",method = RequestMethod.POST)
//    public ApiResult<Object> deleteInport(@Valid @RequestBody Vip p){
//        String id = p.getId();
//        int result = vipService.executeDelete(id);
//        if(result > 0) {
//            return ApiResult.success(null,"删除成功");
//        }
//        return ApiResult.failed("删除失败");
//    }
//
//    @RequestMapping(value = "/edit",method = RequestMethod.POST)
//    public ApiResult<Object> editInport(@Valid @RequestBody VipEditDTO dto){

//        System.out.println(dto);
//        int result = vipService.executeEdit(dto);
//        if(result > 0){
//            return ApiResult.success(null,"编辑成功");
//        }
//        return ApiResult.failed("编辑失败");
//    }
//
//    @RequestMapping(value = "/condition", method = RequestMethod.POST)
//    public ApiResult<List<Vip>> getConditionInport(@Valid @RequestBody VipConditionDTO dto) {
//        System.out.println(dto);
//        List<Vip> l =  vipService.getCondition(dto);
//        return ApiResult.success(l);
  //  }
}

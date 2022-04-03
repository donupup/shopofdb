package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.Provider;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Vip;
import com.example.demo.service.IVipService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vip")
public class VipController {

    @Resource
    IVipService vipService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ApiResult<List<Vip>> getVipList() {
        List<Vip> list = vipService.getList();
        System.out.println(list);
        return ApiResult.success(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public  ApiResult<Object> addvip(@Valid @RequestBody VipAddDTO dto){

        Vip p = vipService.executeAdd(dto);
        if (ObjectUtils.isEmpty(p)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("vip", p);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteVip(@Valid @RequestBody Vip p){
        String id = p.getId();
        int result = vipService.executeDelete(id);
        if(result > 0) {
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ApiResult<Object> editVip(@Valid @RequestBody VipEditDTO dto){

        System.out.println(dto);
        int result = vipService.executeEdit(dto);
        if(result > 0){
            return ApiResult.success(null,"编辑成功");
        }
        return ApiResult.failed("编辑失败");
    }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    public ApiResult<List<Vip>> getConditionVip(@Valid @RequestBody VipConditionDTO dto) {
        System.out.println(dto);
        List<Vip> l =  vipService.getCondition(dto);
        return ApiResult.success(l);
    }
}

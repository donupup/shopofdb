package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.Provider;
import com.example.demo.model.entity.Vip;
import com.example.demo.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private IProviderService providerService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ApiResult<List<Provider>> getproviderList() {
        List<Provider> list = providerService.getList();
        return ApiResult.success(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public  ApiResult<Object> addProvider(@Valid @RequestBody ProviderAddDTO dto){
//        System.out.println(123);
//        System.out.println(dto);
        Provider p = providerService.executeAdd(dto);
        if (ObjectUtils.isEmpty(p)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("provider", p);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteProvider(@Valid @RequestBody Provider p){
        String id = p.getId();
        int result = providerService.executeDelete(id);
        if(result > 0) {
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ApiResult<Object> editProvider(@Valid @RequestBody ProviderEditDTO dto){

        System.out.println(dto);
        int result = providerService.executeEdit(dto);
        if(result > 0){
            return ApiResult.success(null,"编辑成功");
        }
        return ApiResult.failed("编辑失败");
    }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    public ApiResult<List<Provider>> getConditionProvider(@Valid @RequestBody ProviderConditionDTO dto) {
        System.out.println(dto);
        List<Provider> l =  providerService.getCondition(dto);
        return ApiResult.success(l);
    }
}


package com.example.demo.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.GoodAddDTO;
import com.example.demo.model.dto.GoodEditDTO;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.User;
import com.example.demo.service.IGoodService;
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
}

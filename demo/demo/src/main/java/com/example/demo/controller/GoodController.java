package com.example.demo.controller;


import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.User;
import com.example.demo.service.IGoodService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
}

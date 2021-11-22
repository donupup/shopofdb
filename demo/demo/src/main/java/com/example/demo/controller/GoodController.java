package com.example.demo.controller;


import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.User;
import com.example.demo.service.IGoodService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}

package com.example.demo.controller;

import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Good;
import com.example.demo.service.IGoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController extends BaseController{
    @Resource
    private IGoodService goodService;

    @GetMapping
    public ApiResult<List<Good>> searchList(@RequestParam("keyword") String keyword) {
        System.out.println(keyword);
        List<Good> results = goodService.searchByKey(keyword);
        return ApiResult.success(results);
    }

}


package com.example.demo.controller;

import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Provider;
import com.example.demo.service.ICategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    ICategoryService categoryService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ApiResult<List<Category>> getcategoryList() {
        List<Category> list = categoryService.getList();
        return ApiResult.success(list);
    }
}

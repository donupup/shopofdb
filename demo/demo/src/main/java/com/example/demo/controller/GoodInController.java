package com.example.demo.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.*;
import com.example.demo.model.vo.GoodInInfo;
import com.example.demo.service.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

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
        List<GoodIn> l = goodInService.getlist();
        List<GoodInInfo> res = new ArrayList<>();
        for (GoodIn g:l
             ) {
            String pname = this.providerService.getNameById(g.getProviderId());
            String username = this.userService.getUserById(g.getUserId()).getUsername();
            String goodname = this.igoodService.getById(g.getGoodId()).getGoodname();
            String date = DateUtil.formatDateTime(g.getGoodInTime());
            float goodprice = this.igoodService.getById(g.getGoodId()).getPricein();
            GoodInInfo gii =  GoodInInfo.builder().goodPrice(goodprice).providerName(pname).goodName(goodname).id(g.getId()).bio(g.getBio())
                    .num(g.getNum()).userName(username).goodInTime(date).build();
            res.add(gii);

        }
        return ApiResult.success(res);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteInport(@Valid @RequestBody GoodInInfo gi) {

        String id = gi.getId();
        Good g = this.igoodService.getById(this.goodInService.getById(gi.getId()).getGoodId());
        g.setStorage(g.getStorage() - gi.getNum());
        this.igoodService.updateById(g);
        int result = goodInService.executeDelete(id);
        if(result > 0) {
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");

    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
   public ApiResult<Object> editInport(@Valid @RequestBody GoodInEditDTO dto) {

        Good g = this.igoodService.getById(this.goodInService.getById(dto.getId()).getGoodId());
        int oldnum = this.goodInService.getById(dto.getId()).getNum();
        int storage = g.getStorage();
        storage = storage - oldnum + dto.getNum();
        g.setStorage(storage);
        this.igoodService.updateById(g);
        int result  = goodInService.executeEdit(dto);
        if(result > 0){
            return ApiResult.success(null,"编辑成功");
        }
        return ApiResult.failed("编辑失败");
    }
   @RequestMapping(value = "/add",method = RequestMethod.POST)
   public  ApiResult<Object> addInport(@Valid @RequestBody GoodInAddDTO dto){

       GoodIn g = goodInService.executeAdd(dto);
       if (ObjectUtils.isEmpty(g)) {
           return ApiResult.failed("添加失败");
       }
       Good good = igoodService.getById(dto.getGoodid());
       int old_storage = good.getStorage();
       good.setStorage(old_storage + dto.getNum());
       igoodService.updateById(good);
        Map<String, Object> map = new HashMap<>(16);
        map.put("goodin", g);
        return ApiResult.success(map);

   }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    public ApiResult<List<GoodInInfo>> getConditionInport(@Valid @RequestBody GoodInConditionDTO dto) {
        System.out.println(dto);
        List<GoodIn> l =  goodInService.getCondition(dto);
        List<GoodInInfo> list = new ArrayList<>();
        for (GoodIn g:l
        ) {
            String pname = this.providerService.getNameById(g.getProviderId());
            String username = this.userService.getUserById(g.getUserId()).getUsername();
            String goodname = this.igoodService.getById(g.getGoodId()).getGoodname();
            String date = DateUtil.formatDateTime(g.getGoodInTime());
            float goodprice = this.igoodService.getById(g.getGoodId()).getPricein();
            GoodInInfo gii =  GoodInInfo.builder().goodPrice(goodprice).providerName(pname).goodName(goodname).id(g.getId()).bio(g.getBio())
                    .num(g.getNum()).userName(username).goodInTime(date).build();
            list.add(gii);
        }
        return ApiResult.success(list);
    }

    @RequestMapping(value = "/file_good",method = RequestMethod.POST)
    public ApiResult<Object> editGoodByFile(@Valid  @RequestBody JSONArray ja) throws ParseException {

        System.out.println(ja);
        for(int i = 0;i < ja.size();i++) {
            JSONObject good = ja.getJSONObject(i);
            String goodname = good.get("商品名称").toString();
            String providername = good.get("供货商名称").toString();
            String username = good.get("操作员").toString();
            String time = good.get("时间").toString();
            String bio = good.get("备注").toString();
            String goodNum = good.get("数量").toString();
            Date d = DateUtil.parse(time,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            d = DateUtil.offsetHour(d,8);
            System.out.println(d);

            QueryWrapper<Provider> prowra = new QueryWrapper<>();
            LambdaQueryWrapper<Provider> prolambda = prowra.lambda();
            prolambda.eq(Provider::getPname,providername);
            Provider provider = providerService.getBaseMapper().selectOne(prolambda);
            if(ObjectUtils.isEmpty(provider))
            {
                return ApiResult.failed("系统中还没有该供货商，请添加");
            }
            else
            {
                String pid = provider.getId();
                QueryWrapper<Good> goodwra = new QueryWrapper<>();
                LambdaQueryWrapper<Good> goodlambda = goodwra.lambda();
                goodlambda.eq(Good::getProviderId,pid);
                List<Good> goods = igoodService.getBaseMapper().selectList(goodlambda);
                List<String> goodsName = new ArrayList<>();
                for (Good g:goods
                     ) {
                    goodsName.add(g.getGoodname());
                }
                if(!goodsName.contains(goodname))
                {
                    ApiResult.failed("该供货商还没有在系统中注册该商品");
                }
                else{
                    goodlambda.eq(Good::getGoodname,goodname);
                    Good fianlgood = igoodService.getBaseMapper().selectOne(goodlambda);
                    String gid = fianlgood.getId();

                    QueryWrapper<User> userwra = new QueryWrapper<>();
                    LambdaQueryWrapper<User> userlambda = userwra.lambda();
                    userlambda.eq(User::getUsername,username);
                    String uid = userService.getBaseMapper().selectOne(userlambda).getId();
                    int num = Integer.parseInt(goodNum);
                    GoodInAddDTO goodIndto = GoodInAddDTO.builder().goodid(gid).bio(bio).pid(pid).num(num).userid(uid).build();
                    GoodIn goodin = this.goodInService.executeAdd(goodIndto);
                    if (ObjectUtils.isEmpty(goodin)) {
                        return ApiResult.failed("添加失败");
                    }
                    Good newgood = igoodService.getById(gid);
                    int old_storage = newgood.getStorage();
                    newgood.setStorage(old_storage + num);
                    igoodService.updateById(newgood);
                    goodin.setGoodInTime(d);
                    goodInService.updateById(goodin);
                    return ApiResult.success("添加成功！");
                }
            }
        }

        return ApiResult.success();
    }

}

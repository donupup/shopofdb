package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.CartAddDTO;
import com.example.demo.model.dto.CartEditDTO;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodSale;
import com.example.demo.model.vo.CartInfo;
import com.example.demo.service.ICartService;
import com.example.demo.service.IGoodService;
import com.example.demo.service.IUserService;
import com.example.demo.service.IVipService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    ICartService cartService;
    @Resource
    IUserService userService;
    @Resource
    IGoodService goodService;
    @Resource
    IVipService vipService;

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public ApiResult<Object> getCartList(@Valid @RequestBody String vid)
    {
        String[] arr = vid.split("=");
        vid = arr[0];
        QueryWrapper<Cart> cartwra = new QueryWrapper<>();
        LambdaQueryWrapper<Cart> cartlam = cartwra.lambda();
        cartlam.eq(Cart::getVipId,vid);
        List<Cart> cart = cartService.getBaseMapper().selectList(cartlam);
        if(cart.isEmpty()){
            return ApiResult.success();
        }
        List<CartInfo> cartInfo = new ArrayList<>();
        for (Cart item: cart
             ) {
            String vipId = vid;
            String vipName = vipService.getBaseMapper().selectById(vid).getVname();
            String goodId = item.getGoodId();
            String goodName = goodService.getById(item.getGoodId()).getGoodname();
            String userId = item.getUserId();
            String userName = userService.getUserById(userId).getUsername();
            CartInfo cartInfoItem = CartInfo.builder().id(item.getId()).userId(userId).userName(userName).goodId(goodId).goodName(goodName)
                    .vipId(vid).vipName(vipName).num(item.getNum()).price(item.getPrice()).time(item.getAddTime()).build();
            cartInfo.add(cartInfoItem);
        }
        return ApiResult.success(cartInfo);
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ApiResult<Object> addToCart(@Valid @RequestBody CartAddDTO dto)
    {
        System.out.println(dto);
        Good g = goodService.getById(dto.getGoodid());
        if(dto.getNum() > g.getStorage())
        {
            return ApiResult.failed("没有这么多库存了哦");
        }
        int price = dto.getNum() * g.getPricesell();
        Cart cart = Cart.builder().addTime(new Date()).num(dto.getNum()).price(price).goodId(dto.getGoodid()).userId(dto.getUserid()).vipId(dto.getVipid()).build();
        cartService.getBaseMapper().insert(cart);
//        Good g = goodService.getById(dto.getGoodid());
//        g.setStorage(g.getStorage() - dto.getNum());
//        goodService.updateById(g);
        return ApiResult.success();
    }

    @RequestMapping(value="/deleteOne",method = RequestMethod.POST)
    public ApiResult<Object> deleteOneItem(@Valid @RequestBody String id){
        String[] arr = id.split("=");
        id = arr[0];
        int result = cartService.getBaseMapper().deleteById(id);
        if(result > 0){
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @RequestMapping(value="/editOne",method = RequestMethod.POST)
    public ApiResult<Object> editOneItem(@Valid @RequestBody CartEditDTO dto) {

        Cart cartItem = cartService.getById(dto.getId());
        Good g = goodService.getById(cartItem.getGoodId());
        if(dto.getNum() > g.getStorage())
        {
            return ApiResult.failed("库存不够了");
        }
        cartItem.setNum(dto.getNum());
        cartItem.setPrice(dto.getNum() * g.getPricesell());
        cartService.getBaseMapper().updateById(cartItem);
        return ApiResult.success("编辑成功");

    }

    @RequestMapping(value="/deleteMulti",method = RequestMethod.POST)
    public ApiResult<Object> deleteMulti(@Valid @RequestBody JSONArray jsonArray) {
        System.out.println(jsonArray);
        return ApiResult.success();

    }

    @RequestMapping(value="/check",method = RequestMethod.POST)
    public ApiResult<Object> Check(@Valid @RequestBody JSONArray jsonArray) {
        System.out.println(jsonArray);
        return ApiResult.success();

    }

}

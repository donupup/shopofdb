package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.dto.CartAddDTO;
import com.example.demo.model.dto.CartEditDTO;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.Good;
import com.example.demo.model.entity.GoodSale;
import com.example.demo.model.entity.Vip;
import com.example.demo.model.vo.CartInfo;
import com.example.demo.service.*;
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
    @Resource
    IGoodSaleService goodSaleService;

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
        QueryWrapper<Cart> cartwra = new QueryWrapper<>();
        LambdaQueryWrapper<Cart> cartlam = cartwra.lambda();
        cartlam.eq(Cart::getGoodId,g.getId());
        cartlam.eq(Cart::getVipId,dto.getVipid());
        List<Cart> carts = cartService.getBaseMapper().selectList(cartlam);
        if(!carts.isEmpty())
        {
            return ApiResult.failed("该用户已经有此商品在购物车，请去购物车查看~");
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
        for(int i = 0;i < jsonArray.size(); i ++)
        {
           JSONObject jsonObj = jsonArray.getJSONObject(i);
           String id = jsonObj.get("id").toString();
           int num = Integer.parseInt(jsonObj.get("num").toString());
//         System.out.println(id);
//         System.out.println(num);
//         System.out.println(jsonObj);
            int result = cartService.getBaseMapper().deleteById(id);
        }
        return ApiResult.success("删除成功");

    }

    @RequestMapping(value="/check",method = RequestMethod.POST)
    public ApiResult<Object> Check(@Valid @RequestBody JSONArray jsonArray) {
        System.out.println(jsonArray);
        int sum = 0;
        JSONObject jsonZero = jsonArray.getJSONObject(0);
        String idZEro = jsonZero.get("id").toString();
        Cart cartZero = cartService.getById(idZEro);
        Vip vZero = vipService.getById(cartZero.getVipId());
        for(int i = 0;i < jsonArray.size(); i ++)
        {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            String id = jsonObj.get("id").toString();
            Cart cartItem = cartService.getById(id);
            sum += cartItem.getPrice();
        }
        if(vZero.getVbalance() < sum){
            return ApiResult.failed("会员余额不足");
        }
        for(int i = 0;i < jsonArray.size(); i ++)
        {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            String id = jsonObj.get("id").toString();
//         System.out.println(id);
//         System.out.println(num);
//         System.out.println(jsonObj);
            Cart cartItem = cartService.getById(id);
            int num = cartItem.getNum();
            String goodid = cartItem.getGoodId();
            String userid = cartItem.getUserId();
            String vipid = cartItem.getVipId();

            Vip v = vipService.getById(vipid);
            v.setVbalance(v.getVbalance() - cartItem.getPrice());
            vipService.updateById(v);

            Good g = goodService.getById(goodid);
            g.setStorage(g.getStorage() - cartItem.getNum());
            g.setSalenum(g.getSalenum() + cartItem.getNum());
            goodService.updateById(g);

            GoodSale gs = GoodSale.builder().goodId(goodid).num(num).userId(userid).vipId(vipid).goodSoldTime(new Date()).bio(v.getVname() + "购物车结算").build();
            goodSaleService.getBaseMapper().insert(gs);

            cartService.getBaseMapper().deleteById(id);
        }
        return ApiResult.success("售卖成功");

    }

}

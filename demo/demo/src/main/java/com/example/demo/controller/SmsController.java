package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Sms;
import com.example.demo.model.entity.User;
import com.example.demo.service.IUserService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Resource
    HttpServletRequest request;
    @Resource
    IUserService userService;

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public ApiResult<Object> sendCode(@RequestBody Sms sms) {
        System.out.println(sms);

        int appid = 1400657052;
        String appkey = "be9efef993ad4fbc5354abdf93ee5da6";
        int templateId = 1357981; //模板ID
        String smsSign = "NUDTDon个人公众号";
        try {
            QueryWrapper<User> inw = new QueryWrapper<>();
            LambdaQueryWrapper<User> userl = inw.lambda();
            userl.eq(User::getMobile, sms.getPhoneNumber());
            List<User> users = userService.getBaseMapper().selectList(userl);
            if (users.isEmpty()) {
                return ApiResult.failed("该手机号无效，请联系管理员查看~");
            }
            String[] params = {String.valueOf(new Random().nextInt(899999) + 100000)}; //短信中的参数
            System.out.println(params[0]);
            //把验证码存在servletContext中
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute(sms.getPhoneNumber(), params[0]);


            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", sms.getPhoneNumber(), templateId,
                    params, smsSign, "", "");
            System.out.println(result);
            return ApiResult.success("发送成功，请在手机查看");
        } catch (Exception e) {
            return ApiResult.failed();
        }
    }
}

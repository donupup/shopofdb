package com.example.demo.controller;

import com.example.demo.model.entity.Sms;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Resource
    HttpServletRequest request;

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public void sendCode(@RequestBody Sms sms){
        System.out.println(sms);

        int appid = 1400657052;
        String appkey = "be9efef993ad4fbc5354abdf93ee5da6";
        int templateId = 1357981; //模板ID
        String smsSign = "NUDTDon个人公众号";
        try{
            String[] params = {String.valueOf(new Random().nextInt(899999) + 100000)}; //短信中的参数
            HttpSession session = request.getSession();
            session.setAttribute("verifyCode",params[0]);
            SmsSingleSender ssender = new SmsSingleSender(appid,appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86",sms.getPhoneNumber(),templateId,
                    params,smsSign,"","");
            System.out.println(result);
        }catch (HTTPException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

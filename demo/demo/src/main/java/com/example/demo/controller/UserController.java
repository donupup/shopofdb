package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.demo.common.api.ApiResult;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.GoodIn;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.Sms;
import com.example.demo.model.entity.User;
import com.example.demo.service.IRoleService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.MD5Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Resource
    private IUserService iUserService;

    @Resource
    private IRoleService roleService;
    @Resource
    HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        Map<String, String> map = new HashMap<>(16);
        map = iUserService.executeLogin(dto);
        if (ObjectUtils.isEmpty(map.get("token"))) {
            return ApiResult.failed("账号密码错误");
        }
        return ApiResult.success(map, "登录成功");
    }
    @RequestMapping(value = "/msglogin", method = RequestMethod.POST)
    public ApiResult<Object> Msglogin(@Valid @RequestBody Sms sms) {

        String phoneNumber = sms.getPhoneNumber();
        String code = sms.getCode();

        ServletContext servletContext = request.getServletContext();
        try{
            String verifyCode = servletContext.getAttribute(phoneNumber).toString();
            System.out.println(verifyCode);
            if (!StrUtil.equals(code,verifyCode)) {
                return ApiResult.failed("验证码不正确，请输入正确的验证码");
            } else {
                servletContext.removeAttribute(phoneNumber);
                QueryWrapper<User> inw = new QueryWrapper<>();
                LambdaQueryWrapper<User> userl = inw.lambda();
                userl.eq(User::getMobile, phoneNumber);
                List<User> users = iUserService.getBaseMapper().selectList(userl);
                if (users.isEmpty()) {
                    return ApiResult.failed("该手机号无效，请联系管理员查看~");
                } else {
                    User user = users.get(0);
                    //LoginDTO loginDTO = LoginDTO.builder().username(user.getUsername()).password(user.getPassword()).rememberMe(true).build();
                    String token = null;
                    String role;
                    Map<String, String> map = new HashMap<>(16);
                    token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
                    role = user.getRoleId();
                    String userId = user.getId();
                    map.put("token", token);
                    map.put("role", role);
                    map.put("userId", userId);
                    return ApiResult.success(map,"登录成功");
                }
            }
        }catch (Exception e){
            return ApiResult.failed("请重新发送验证码~");
        }

    }

    @RequestMapping(value = "/msgcheck", method = RequestMethod.POST)
    public ApiResult<Object> Msgcheck(@Valid @RequestBody SecretChangeDTO sms) {

        String phoneNumber = sms.getPhoneNumber();
        String code = sms.getCode();
        ServletContext servletContext = request.getServletContext();
        try{
            String verifyCode = servletContext.getAttribute(phoneNumber).toString();
            System.out.println(verifyCode);
            if (!StrUtil.equals(code,verifyCode)) {
                return ApiResult.failed("验证码不正确，请输入正确的验证码");
            } else {
                servletContext.removeAttribute(phoneNumber);

                User user = iUserService.getUserById(sms.getUserid());
                //LoginDTO loginDTO = LoginDTO.builder().username(user.getUsername()).password(user.getPassword()).rememberMe(true).build();

                return ApiResult.success("身份已验证，可修改密码~");
            }
        }catch (Exception e){
            return ApiResult.failed("请重新发送验证码~");
        }
    }

    @RequestMapping(value = "/secretchange", method = RequestMethod.POST)
    public ApiResult<Object> changeSecret(@Valid @RequestBody PassDTO dto)
    {
        System.out.println(dto);
        if(!StrUtil.equals(dto.getPass(),dto.getCheckpass()))
        {
            return ApiResult.failed("两次输入的密码不一致");
        }
        User user = iUserService.getUserById(dto.getUserid());
        String newpass = MD5Utils.getPwd(dto.getPass());
        user.setPassword(newpass);
        iUserService.updateById(user);
        return ApiResult.success("修改成功！");
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
//        ServletContext servletContext = request.getServletContext();
//        String code = servletContext.getAttribute("verifyCode").toString();
//        System.out.println(code);
        return ApiResult.success(null, "注销成功");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        User user = iUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<List<User>> getUser() {
        List<User> users = iUserService.getUserList();
        return ApiResult.success(users);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public ApiResult<Object> editInfo(@Valid @RequestBody EditDTO dto) {
        System.out.println(dto);
        int result = iUserService.executeEdit(dto);
        if (result <= 0) {
            return ApiResult.failed("修改失败");
        }
        return ApiResult.success(null, "修改成功");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ApiResult<Object> deleteUser(@Valid  @RequestBody String username){
        //这里对传进来的参数进行了处理，因为前后端对RequeBody的处理方式不同，对于对象来说是正常的
        System.out.println(username);
        String[] arr = username.split("=");
        username = arr[0];
        int result = iUserService.executeDelete(username);
        if(result > 0){
            return ApiResult.success(null,"删除成功");
        }
        return ApiResult.failed("删除失败");
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ApiResult<List<Role>> getRole() {
        List<Role> roles = roleService.getRoleList();
        return ApiResult.success(roles);
    }

    @RequestMapping(value = "/condition", method = RequestMethod.POST)
    public ApiResult<List<User>> getConditionUser(@Valid @RequestBody UserConditionDTO dto) {
        System.out.println(dto);
        List<User> l =  iUserService.getCondition(dto);
        return ApiResult.success(l);
    }

    @RequestMapping(value = "/getbyid",method = RequestMethod.POST)
    public  ApiResult<Object> getUserById(@Valid @RequestBody String id)
    {
        String[] arr = id.split("=");
        id = arr[0];
        User user = iUserService.getUserById(id);
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/editbyid",method = RequestMethod.POST)
    public  ApiResult<Object> editUserById(@Valid @RequestBody UserCenterInfoDTO dto)
    {
        System.out.println(dto);

        User user = iUserService.getUserById(dto.getUserid());
        if(!StrUtil.isBlank(dto.getAlias()))
        {
            user.setAlias(dto.getAlias());
        }
        if(!StrUtil.isBlank(dto.getUsername()))
        {
            user.setUsername(dto.getUsername());
        }
        if(!StrUtil.isBlank(dto.getEmail()))
        {
            user.setEmail(dto.getEmail());
        }
        if(!StrUtil.isBlank(dto.getStuNo()))
        {
            user.setStuNo(dto.getStuNo());
        }
        if(!StrUtil.isBlank(dto.getBio()))
        {
            user.setBio(dto.getBio());
        }
        iUserService.updateById(user);
        return  ApiResult.success();
    }

}

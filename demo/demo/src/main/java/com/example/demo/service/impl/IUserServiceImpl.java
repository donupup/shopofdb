package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.api.ApiResult;
import com.example.demo.common.exception.ApiAsserts;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.EditDTO;
import com.example.demo.model.dto.LoginDTO;
import com.example.demo.model.dto.RegisterDTO;
import com.example.demo.model.dto.UserConditionDTO;
import com.example.demo.model.entity.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper usermapper;

    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public Map<String, String> executeLogin(LoginDTO dto) {
        String token = null;
        String role;
        Map<String, String> map = new HashMap<>(16);
        try {
            User user = getUserByUsername(dto.getUsername());
            String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if (!encodePwd.equals(user.getPassword())) {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
            role = user.getRoleId();
            String userId = user.getId();
            map.put("token", token);
            map.put("role", role);
            map.put("userId",userId);
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUsername());
        }
        return map;
    }

    @Override
    public User executeRegister(RegisterDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getName()).or().eq(User::getStuNo, dto.getStuNo());
        User umsUser = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(umsUser)) {
            ApiAsserts.fail("账号或学号已存在！");
        }
        User addUser = User.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(MD5Utils.getPwd(dto.getPass()))
                .stuNo(dto.getStuNo())
                .roleId(dto.getRole())
                .createTime(new Date())
                .mobile(dto.getMobile())
                .status(true)
                .build();
        baseMapper.insert(addUser);

        return addUser;
    }


    @Override
    public List<User> getUserList() {
        List<User> user = usermapper.selectList(null);
        return user;
    }

    @Override
    public int  executeEdit(EditDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User umsUser = new User();
        umsUser.setRoleId(dto.getRole());
        umsUser.setEmail(dto.getEmail());
        umsUser.setMobile(dto.getMobile());
        umsUser.setStuNo(dto.getStuNo());
        umsUser.setAlias(dto.getAlias());
        int result = this.baseMapper.update(umsUser, wrapper);
        return result;
    }

    @Override
    public int executeDelete(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        int result = baseMapper.delete(wrapper);
        return result;
    }

    @Override
    public List<User> getCondition(UserConditionDTO dto) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = wrapper.lambda();
        if(!StrUtil.isBlank(dto.getUsername()))
        {
            lambda.like(User::getUsername,dto.getUsername());
        }
        if(!StrUtil.isBlank(dto.getStuNo()))
        {
            lambda.eq(User::getStuNo,dto.getStuNo());
        }
        if(!StrUtil.isBlank(dto.getRole()))
        {
            lambda.eq(User::getRoleId,dto.getRole());
        }

        List<User> users = usermapper.selectList(lambda);
        return  users;

    }

    @Override
    public User getUserById(String id) {
        return this.baseMapper.selectById(id);
    }
}

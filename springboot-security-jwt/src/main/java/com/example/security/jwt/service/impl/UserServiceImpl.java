package com.example.security.jwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.security.jwt.common.enums.RoleEnum;
import com.example.security.jwt.common.utils.ResponseUtils;
import com.example.security.jwt.dao.domain.User;
import com.example.security.jwt.dao.mapper.UserMapper;
import com.example.security.jwt.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseUtils listUser() {
        List<User> users = userMapper.selectList(null);
        return ResponseUtils.success(users);
    }

    @Override
    public User getUserInfo(String username){
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("username",username);
        return userMapper.selectOne(objectQueryWrapper);
    }

    @Override
    public ResponseUtils insertUser(User userInfo){
        // 加密密码
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        //默认角色
        userInfo.setRoleId(RoleEnum.NORMAL.getId());
        return ResponseUtils.success(userMapper.insert(userInfo));
    }

    @Override
    public ResponseUtils updatePwd(String oldPwd, String newPwd) {
        // 获取当前登录用户信息(注意：没有密码的)
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();

        // 通过用户名获取到用户信息（获取密码）
        User userInfo = this.getUserInfo(username);

        // 判断输入的旧密码是正确
        if (passwordEncoder.matches(oldPwd, userInfo.getPassword())) {
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.lambda().eq(User::getUsername, username);
            //加密新密码
            String encode = passwordEncoder.encode(newPwd);
            userInfo.setPassword(encode);
            userMapper.update(userInfo, wrapper);
        }
        return ResponseUtils.success();
    }

}

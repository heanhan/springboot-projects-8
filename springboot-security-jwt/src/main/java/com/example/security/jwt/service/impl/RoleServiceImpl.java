package com.example.security.jwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.security.jwt.dao.domain.Role;
import com.example.security.jwt.dao.mapper.RoleMapper;
import com.example.security.jwt.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public String getUserRole(Integer id) {
        return roleMapper.selectOne(new QueryWrapper<Role>().eq("id", id)).getRoleName();
    }
}

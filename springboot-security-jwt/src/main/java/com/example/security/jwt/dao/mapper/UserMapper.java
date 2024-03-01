package com.example.security.jwt.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.security.jwt.dao.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

}
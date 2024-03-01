package com.example.security.jwt.service;


import com.example.security.jwt.common.utils.ResponseUtils;
import com.example.security.jwt.dao.domain.User;


public interface UserService {

    ResponseUtils listUser();

    User getUserInfo(String username);

    ResponseUtils insertUser(User userInfo);

    ResponseUtils updatePwd(String oldPwd, String newPwd);
}

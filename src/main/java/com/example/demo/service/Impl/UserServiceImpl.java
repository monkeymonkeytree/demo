package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean UserExist(User user) {
        User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        return findUser != null;
    }

    @Override
    public void InsertUser(User user) {
        userMapper.insertUser(user);
    }


}

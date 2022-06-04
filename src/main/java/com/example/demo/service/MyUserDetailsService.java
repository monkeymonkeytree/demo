package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.demo.bean.SecurityUser;
import com.example.demo.bean.User;
import com.example.demo.filter.DefaultPasswordEncoder;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> user = new QueryWrapper<User>().eq("username", username);
        User findUser = userMapper.selectOne(user);
        if (findUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(findUser);
        securityUser.setPermissionValueList(Arrays.asList(findUser.getRole()));
        System.out.println(findUser.getRole());
        return securityUser;

    }


}

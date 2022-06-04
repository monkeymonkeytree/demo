package com.example.demo.service;

import com.example.demo.bean.User;

public interface UserService {

   boolean UserExist(User user);

   void InsertUser(User user);
}

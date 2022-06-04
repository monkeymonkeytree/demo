package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.utils.MD5;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/regist")
    public String regist(User user, Model model) {
        if (userService.UserExist(user)) {
            return "redirect:register.html";
        } else {
            System.out.println(user);
            userService.InsertUser(user);
            model.addAttribute("user", user);
            return "redirect:login";
        }

    }

    @ResponseBody
    @PutMapping("/whoami")
    public User whoami(HttpSession session) {
        String username = session.getAttribute("user") + "";
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return user;
    }

    @ResponseBody
    @PutMapping("/updateUser")
    public User update(User user) {
        System.out.println(user);
        User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        Integer id = findUser.getId();
        user.setId(id);
        System.out.println(user);
        userMapper.updateById(user);
        return user;
    }

    @ResponseBody
    @PutMapping("/selectAll")
    public Object selectAll(HttpSession session) {
        List<User> users = userMapper.selectList(null);
//        for (User user :users) {
//
//        }

        HashMap map = new HashMap<>();
        map.put("users", users);
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Object updateAdmin(User user) {
        System.out.println(user);
        HashMap map = new HashMap<>();
        if (user.getPassword().length() == 32) {
            user.setPassword(null);
        }
        System.out.println(user);
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            map.put("msg", "更新失败！用户名和别人重复");
            return map;
        }
        map.put("msg", "更新成功！");
        return map;
    }


    @ResponseBody
    @PutMapping("/insert")
    public Object insert(User user) {
        System.out.println(user);
        HashMap<Object, Object> map = new HashMap<>();
        if (userService.UserExist(user)) {
            map.put("msg", "添加失败,该用户名已存在");
            return map;
        } else {
            user.setPassword(MD5.encrypt(user.getPassword()));
            userMapper.insert(user);
            map.put("msg", "添加成功！");
            return map;
        }
    }


    @ResponseBody
    @PutMapping("findpassword")
    public Object findPassword(User user) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        try {
            User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
            System.out.println(user.getUsername());
            String email = findUser.getEmail();
            objectObjectHashMap.put("msg", "已将密码发送至你的邮箱" + email);
        } catch (Exception e) {
            objectObjectHashMap.put("msg","没有找到这个用户");
        }
        return objectObjectHashMap;
    }

    @ResponseBody
    @PutMapping("/regist")
    public Object checkUser(User user) {

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        System.out.println(user);
        if (userService.UserExist(user)) {
            objectObjectHashMap.put("msg", "用户名已存在");
            return objectObjectHashMap;
        } else {
            objectObjectHashMap.put("msg", "");
            return objectObjectHashMap;
        }
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";

    }

    @GetMapping("/")
    public String main() {
        return "index.html";
    }


    @ResponseBody
    @DeleteMapping("/delete")
    public Object delete(Integer id) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        try {
            userMapper.deleteById(id);
            objectObjectHashMap.put("msg", "删除成功");
        } catch (Exception e) {
            objectObjectHashMap.put("msg", "删除失败");
        }

        return objectObjectHashMap;
    }

}

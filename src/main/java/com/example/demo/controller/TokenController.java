package com.example.demo.controller;

import com.example.demo.filter.TokenManager;
import com.example.demo.utils.utils.R;
import com.example.demo.utils.utils.ResponseUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class TokenController {

    @Autowired
    TokenManager tokenManager;

    @PostMapping("/login")
    public String login(HttpSession httpSession, Model model, HttpServletResponse response,HttpServletRequest request) {
        R msg = null;
        Integer code = null;
        try {
            msg = (R) httpSession.getAttribute("msg");
            code = msg.getCode();
            if (httpSession.getAttribute("token") != null) {
                log.info("token:" + httpSession.getAttribute("token").toString());
                String user = tokenManager.getUserFromToken(httpSession.getAttribute("token").toString());
                httpSession.setAttribute("user", user);
                log.info("user:" + user);
                model.addAttribute("user", user);
            }
        } catch (Exception e) {
            ResponseUtil.out(response, R.error());
        }


        log.info("json" + msg.toString());
        if (code == 20000) {
            return "redirect:/usercenter.html";
        } else {
            return "redirect:/login.html";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        return "redirect:/homepage.html";
    }

}

package com.example.demo.filter;

import com.example.demo.bean.User;
import com.example.demo.utils.exceptionhandler.GuliException;
import com.example.demo.utils.utils.R;
import com.example.demo.utils.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager,
                                     TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            logger.info("=================" + req.getRequestURI());
//        if (req.getRequestURI().indexOf("管理员") == -1) {
//            chain.doFilter(req, res);
//            return;
//        }
            List<String> role = null;
            HttpSession session = req.getSession();
            Object token = session.getAttribute("token");
            String s = req.getRequestURI();
            if (s.equals("/admincenter.html") || s.equals("/usercenter.html")||s.equals("/updateUser")
                    || s.equals("/userdevicemanage.html") || s.equals("/developdevicemanage.html")||s.equals("/deleteMyCGQ")||
                    s.equals("/selectCGQ")||s.equals("/updateCGQ")||s.equals("/selectData")||s.equals("/selectAll")) {

                if (token == null) {
                    try {
                        req.getRequestDispatcher("/login").forward(req, res);
                    }catch (Throwable e) {
                        ResponseUtil.out(res, R.error());
                        return;
                    }
                    return;
                } else {
                    String user = tokenManager.getUserFromToken(token.toString());
                    log.info(user);
                    role = (List<String>) redisTemplate.opsForValue().get(user);
                    log.info(role + "");
                    if (role == null) {
                        try {
                            req.getRequestDispatcher("/login").forward(req, res);
                            return;
                        } catch (Exception e) {
                            ResponseUtil.out(res, R.error());
                            return;
                        }
                    }
                    if (role.contains("管理员") && s.equals("/developdevicemanage.html")) {
                        req.getRequestDispatcher("/unauth.html").forward(req, res);
                        return;
                    }

                    if (role.contains("开发者") && s.equals("/admincenter.html")) {
                        req.getRequestDispatcher("/unauth.html").forward(req, res);
                        return;
                    }

                    if (role.contains("普通用户") && (s.equals("/admincenter.html") || s.equals("/developdevicemanage.html"))) {
                        req.getRequestDispatcher("/unauth.html").forward(req, res);
                        return;
                    }
                }
            }

            if (((s.equals("/login.html") || s.equals("/homepage.html")) || s.equals("/register.html")) && token != null) {
                req.getRequestDispatcher("/usercenter.html").forward(req, res);
                return;
            }

            if (s.equals("/insert")||s.equals("/delete")||s.equals("/selectAll")) {
                String user = tokenManager.getUserFromToken(token.toString());
                log.info(user);
                role = (List<String>) redisTemplate.opsForValue().get(user);
                if (!(role != null && role.contains("管理员"))) {
                    throw new GuliException(2022, "你没有权限");
                }
            }

            if (s.equals("/insertCGQ")||s.equals("/deleteCGQ")) {
                String user = tokenManager.getUserFromToken(token.toString());
                log.info(user);
                role = (List<String>) redisTemplate.opsForValue().get(user);
                if (!(role != null && role.contains("开发者"))) {
                    throw new GuliException(2022, "你没有权限");
                }
            }

        } catch (Throwable e) {
            ResponseUtil.out(res, R.error());
            return;
        }


        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, R.error());
        }
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(res, R.error());
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken
    getAuthentication(HttpServletRequest request) {
        // token 置于 session 里
        HttpSession session = request.getSession();
        String token = session.getAttribute("token").toString();
        if (token != null && !"".equals(token.trim())) {
            String userName = tokenManager.getUserFromToken(token);
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                if (StringUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }
            if (!StringUtils.isEmpty(userName)) {
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            return null;
        }
        return null;
    }
}
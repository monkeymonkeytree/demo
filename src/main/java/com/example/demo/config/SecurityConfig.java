package com.example.demo.config;

import com.example.demo.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin();
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint()).and().addFilter(new TokenLoginFilter(authenticationManager(),
                        tokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate))
                .httpBasic();

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/homepage.html").addLogoutHandler(new
                TokenLogoutHandler(tokenManager, redisTemplate));

        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login").defaultSuccessUrl("/homepage.html.html")
                .and().authorizeRequests()
                .antMatchers("/login", "/index.html", "/**/*.css", "/**/*.js", "/**/*.gif"
                        , "/**/*.svg", "/**/*.ico", "/**/*.png", "/**/*.jpeg", "/**/*.crx", "/**/*.woff2",
                        "/resources/**/*.*", "/token").permitAll()
                .and().csrf().disable();
//        http.exceptionHandling().accessDeniedPage("/unauth.html");

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**", "/swagger-ui.html/**").antMatchers("/login", "/index.html", "/**/*.css", "/**/*.js", "/**/*.gif"
                , "/**/*.svg", "/**/*.ico", "/**/*.png", "/**/*.jpeg", "/**/*.crx", "/**/*.woff2",
                "/resources/**/*.*");
    }


}

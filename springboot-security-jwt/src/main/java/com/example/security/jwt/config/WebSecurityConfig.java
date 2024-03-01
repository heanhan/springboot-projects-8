package com.example.security.jwt.config;

import com.alibaba.fastjson.JSONObject;
import com.example.security.jwt.common.utils.JwtTokenUtils;
import com.example.security.jwt.common.utils.ResponseUtils;
import com.example.security.jwt.config.filter.JWTAuthenticationFilter;
import com.example.security.jwt.config.filter.JWTAuthorizationFilter;
import com.example.security.jwt.config.springsecurity.CustomUserDetailsService;
import com.example.security.jwt.config.springsecurity.JWTAccessDeniedHandler;
import com.example.security.jwt.config.springsecurity.JWTAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomUserDetailsService userDatailService;

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // post请求要关闭csrf验证,不然访问报错；实际开发中开启，需要前端配合传递其他参数
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                //放开注册,登录用户接口
                .antMatchers("/user/register").anonymous()
                .antMatchers("/user/login").anonymous()
                .anyRequest().authenticated() // 所有请求都需要验证
                .and()
                .formLogin() // 使用默认的登录页面
                .and()
                .logout().logoutUrl("/user/logout")
                .logoutSuccessHandler(jwtLogoutSuccessHandler())
                .and()
                //添加用户账号的认证
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                //添加用户权限的认证
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                //添加没有携带token或者token无效操作
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                //添加无权限时的处理
                .accessDeniedHandler(new JWTAccessDeniedHandler())
        ;
    }

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 从数据库读取的用户进行身份认证
                .userDetailsService(userDatailService)
                .passwordEncoder(passwordEncoder());
    }


    /**
     * 退出登录
     * @return
     */
    @Bean
    public LogoutSuccessHandler jwtLogoutSuccessHandler() {
        return (request, response, authentication) -> {
            //TODO 登录用户加入redis
            String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
            JwtTokenUtils.invalidateToken(token);
            // 清除响应中的JWT相关信息
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(ResponseUtils.success("退出成功")));
            response.setStatus(HttpServletResponse.SC_OK);
        };
    }
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception{
//        //调试阶段
//        httpSecurity.csrf().disable().authorizeRequests();
//        httpSecurity.authorizeRequests().anyRequest()
//                .permitAll().and().logout().permitAll();
//    }


}
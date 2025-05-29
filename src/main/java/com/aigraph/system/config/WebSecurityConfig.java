package com.aigraph.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 允许静态资源访问
                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                // 登录和注册页不需要认证
                .antMatchers("/login", "/register").permitAll()
                // 其它请求需要认证
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")                    // 自定义登录页
                .defaultSuccessUrl("/dashboard")        // 登录成功后跳转
                .failureUrl("/login?error=true")        // 登录失败后跳转
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true") // 注销成功后跳转
                .invalidateHttpSession(true)            // 会话失效
                .deleteCookies("JSESSIONID")            // 删除cookie
                .and()
            .rememberMe()                              // 记住我功能
                .key("aigraphUniqueKey")               // 加密密钥
                .tokenValiditySeconds(2592000)         // 有效期30天
                .rememberMeParameter("remember-me")    // 请求参数名
                .and()
            .csrf();                                   // 启用CSRF保护
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN");
        // 后续可以改为使用数据库认证
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 
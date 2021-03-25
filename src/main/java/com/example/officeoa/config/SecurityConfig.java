package com.example.officeoa.config;

import com.example.officeoa.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 15:17 2021/3/4
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsServiceImpl UserDetailsServiceImpl;

    /**
     * 重写configure(HttpSecurity http)方法，对用户的进行授权管理
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/loginPost")
                .successForwardUrl("/index")
                .and()
                .sessionManagement()
                //设置session超时时跳转的url
                .invalidSessionUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/login","/login.html").permitAll()
                .antMatchers(new String[]{"/js/**","/css/**","/images/**","/font/**","/json/**"}).permitAll()
                .anyRequest().authenticated().and().csrf().disable();

        //设置登出页面
        http.logout().logoutUrl("/logout");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.headers().frameOptions().sameOrigin();
        http.headers().frameOptions().disable();

    }

    /**
     * 重写configure(AuthenticationManagerBuilder auth)，对用户进行认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

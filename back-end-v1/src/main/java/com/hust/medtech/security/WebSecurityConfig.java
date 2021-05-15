package com.hust.medtech.security;


import com.hust.medtech.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {

    @Autowired
    AccountServiceImpl accountService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    @Bean()
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    //    @Bean
//    public PasswordEncoder passwordEncoder() {
//      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                // auth.userDetailsService(userService) // Cung cáp userservice cho spring
                // security
                .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // config loi 403

        http.cors(); // Ngăn chặn request từ một domain khác
        // Cho phép tất cả mọi người truy cập
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/getUserInfo").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/getNews").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/doctor/**").access("hasRole('ADMIN')");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/doctor/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/phieu-kham").permitAll();


        http.authorizeRequests().anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được
        // truy cập
        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
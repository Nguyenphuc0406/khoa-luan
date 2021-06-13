package com.hust.medtech.security;


import com.hust.medtech.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"};
    // other public endpoints of your API may be appended to this array

    @Value("${environment.name}")
    private  String profile;
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
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/doctor/**").access("hasRole('ADMIN')");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/doctor/**").access("hasRole('ADMIN')");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/phieu-kham").access("hasRole('USER')");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/patient/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/device").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/patient/**").access("hasRole('USER')");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/notify").permitAll();

        http.authorizeRequests().
                antMatchers(AUTH_WHITELIST).permitAll().  // whitelist Swagger UI resources
                // ... here goes your custom security configuration
                        antMatchers("/**").authenticated();
        http.csrf().disable().authorizeRequests().and().logout().logoutUrl("/logout").permitAll();

        http.authorizeRequests().anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được
        // truy cập
        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
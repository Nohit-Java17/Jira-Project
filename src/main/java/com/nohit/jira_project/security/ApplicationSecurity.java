package com.nohit.jira_project.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.savedrequest.*;

import com.nohit.jira_project.filter.*;

import static com.nohit.jira_project.constant.ViewConstant.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSessionRequestCache httpSessionRequestCache;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Chống dùng session tấn công
                .csrf().disable()
                // cors: Không cho phép truy cập vào tài nguyên nếu không đúng domain, mặc định
                // enable
                // Vì enable nên nếu không quy định domain nào được phép gọi nó thì sẽ lỗi 403
                .cors().disable()
                // NOTE
                // Khai báo không sử dụng Session
                .sessionManagement().sessionCreationPolicy(STATELESS)
                // Định nghĩa link chứng thực
                .and().authorizeHttpRequests()
                // Khi vào link login và refresh thì cho qua không cần Authen
                .antMatchers(API_VIEW + LOGIN_VIEW, API_VIEW + TOKEN_VIEW + REFRESH_VIEW).permitAll()
                .anyRequest().authenticated()
                // Chạy filter jwtAuthFilter() trước filter chứng thực
                // UsernamePasswordAuthenticationFilter
                .and().addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }
}

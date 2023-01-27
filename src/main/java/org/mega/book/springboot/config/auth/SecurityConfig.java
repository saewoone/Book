package org.mega.book.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.mega.book.springboot.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService cunstomOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/js/**","h2-console/**","/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .and()
                .logout().logoutSuccessUrl("/")
                .and().oauth2Login().userInfoEndpoint().userService(cunstomOAuth2UserService); //구글로그인하면 이 서비스 쓰겠다는거..
        //시큐리티한테 승인을 받아야돼요
    }
}

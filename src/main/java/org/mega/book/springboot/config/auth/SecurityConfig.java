package org.mega.book.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.mega.book.springboot.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService; //스프링에 등록되어 있는거 받아옴
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable() //csrf다 없앰
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/js/**","h2-console/**","/profile").permitAll() //요청와도 제외시킬 애들
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //로그인돼있는경우
                .and()
                .logout().logoutSuccessUrl("/")
                .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService); //구글로그인하면 이 서비스 쓰겠다는거..
            //인증 관련된 로그인은 customOAuth... 얘한테 시키겠다
        //시큐리티한테 승인을 받아야돼요
    }
}

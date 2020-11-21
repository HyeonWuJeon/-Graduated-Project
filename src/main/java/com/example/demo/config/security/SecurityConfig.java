package com.example.demo.config.security;

import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;

    /**
     * bean :: bcrypt 암호화
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 정적파일 인증 무시
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/css/**", "/memberAuth/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                .authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole(Role.ADMIN.name()) // 관리자만 접근 허용
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name()) // 관리자만 접근 허용
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/resources/**").permitAll()
//                .anyRequest().authenticated()
                .and()//로그인
                .formLogin()
                .loginPage("/member/login") // 로그인 페이지
                .defaultSuccessUrl("/").permitAll() // 성공시 메인페이지
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃
                .logoutSuccessUrl("/") // 성공시 메인페이지
                .invalidateHttpSession(true)
                .and()
                //403 예외처리 핸들링 권한거부
                .exceptionHandling().accessDeniedPage("/member/denied")
                .and().headers().frameOptions().disable();
        /* 구글 로그인 필터 */
//                .and()
//                    .oauth2Login()
//                        .userInfoEndpoint()
//                            .userService(customOAuth2UserService);

    }

    /**
     * UserDetailService :: 유저정보 인터페이스 커스텀 memberService //bcrypt 암호화
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }


}
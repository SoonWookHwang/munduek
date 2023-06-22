package com.example.MunDeuk.security;

import com.example.MunDeuk.repository.RefreshTokenRepository;
import com.example.MunDeuk.security.jwt.JwtAuthenticationFilter;
import com.example.MunDeuk.security.jwt.JwtTokenProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  private final RefreshTokenRepository refreshTokenRepository;

  private final String[] allowedUrls = {"/","/login"};

  public WebSecurityConfig(JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Bean
  public BCryptPasswordEncoder encodePassword() {
    return new BCryptPasswordEncoder(13);
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeHttpRequests().requestMatchers("/h2-console/*").permitAll();
    http.headers(headers -> headers
        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    //-> http.headers(headers->frame).frameOptions().disable();
    http.authorizeHttpRequests(requests ->
            requests.requestMatchers(HttpMethod.GET, allowedUrls).permitAll()
                .anyRequest().authenticated())
        // 세션인증 사용하지 않음
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin()
        .loginPage("/login.html")
        .and()
        .logout()
        .logoutSuccessUrl("/index.html")
        .and()
        // 토큰인증 먼저 실행
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, refreshTokenRepository),
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }


}
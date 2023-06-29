package com.example.MunDeuk.service;

import com.example.MunDeuk.dto.memberDto.LoginRequestDto;
import com.example.MunDeuk.dto.memberDto.TokenDto;
import com.example.MunDeuk.global.errors.CustomErrorCode;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.models.postgres.Member;
import com.example.MunDeuk.repository.postgres.MemberRepository;
import com.example.MunDeuk.repository.redis.RefreshTokenRepository;
import com.example.MunDeuk.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final MemberRepository memberRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;


  public TokenDto login(LoginRequestDto dto, HttpServletResponse response){
    log.info("[AuthService] login 진입");
    String username = dto.getUsername();
    log.info("[AuthService] username =" + username);
    Member member = memberRepository.findByUsername(username).orElseThrow(()->new MunDeukRuntimeException(
        CustomErrorCode.USER_NOT_FOUND));
    if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
      log.info("[AuthService] 패스워드 확인");
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
    String existRefreshToken = refreshTokenRepository.findRefreshTokenByUserId(member.getId());
    log.info("[login] DB에 refreshToken이 저장되어있는지 확인");

    if(existRefreshToken!=null) {
      refreshTokenRepository.deleteRefreshToken(existRefreshToken);
      log.info("[login] 기존 refreshToken 삭제");
    }

    String refreshToken = jwtTokenProvider.createRefreshToken();
    log.info("[login] refreshToken 생성");


    refreshTokenRepository.saveRefreshToken(refreshToken, member.getId(),60*60*24*30);
    log.info("[login] DB에 refreshToken 새롭게 저장");


    Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", refreshToken);
    refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    response.addCookie(refreshTokenCookie);
    log.info("[login] 쿠키에 refreshToken 전달");

    TokenDto tokenDto = TokenDto.builder()
        .accessToken(jwtTokenProvider.createToken(member.getUsername(), member.getRoles()))
        .build();
    return tokenDto;
  }




}

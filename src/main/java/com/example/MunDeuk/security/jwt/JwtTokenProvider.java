package com.example.MunDeuk.security.jwt;

import com.example.MunDeuk.repository.MemberRepository;
import com.example.MunDeuk.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
  private final UserDetailsService userDetailsService; // Spring Security 에서 제공하는 서비스 레이어

  private final RefreshTokenRepository refreshTokenRepository;

  private final MemberRepository memberRepository;

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.refresh-key}")
  private String refreshKey;
  private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간 토큰 유효

  private final long refreshValidMillisecond = 1000L * 60 * 60 * 24 * 30; // 30일 토큰 유효

  @PostConstruct
  protected void init() {
    LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");

    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes(StandardCharsets.UTF_8));

    LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
  }
    /*

  -----------------ACCESS-TOKEN--------------------

   */

  public String createToken(String username, List<String> roles) {
    LOGGER.info("[createToken] 토큰 생성 시작");
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles);

    Date now = new Date();
    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
        .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
        .compact();

    LOGGER.info("[createToken] 토큰 생성 완료");
    return token;
  }

  public Authentication getAuthentication(String token) {
    LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
    LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
        userDetails.getUsername());
    return new UsernamePasswordAuthenticationToken(userDetails, "",
        userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
    String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .getSubject();
    LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
    return info;
  }


  public String resolveToken(HttpServletRequest request) {
    LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
    return request.getHeader("X-AUTH-TOKEN");
  }

  public boolean validateToken(String token) {
    LOGGER.info("[validateToken] 토큰 유효 체크 시작");
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      LOGGER.info("[validateToken] 토큰 유효 체크 완료");
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
      return false;
    }
  }


  /*

  -----------------REFRESH-TOKEN--------------------

   */
  public String createRefreshToken() {
    // 키 생성
    Date now = new Date();
    String refreshToken = Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + refreshValidMillisecond))
        .signWith(SignatureAlgorithm.HS256, refreshKey) // 암호화 알고리즘, 리프레시 토큰용 secret 값 세팅
        .compact();

    LOGGER.info("[createRefreshToken] 리프레시 토큰 생성 완료");
    return refreshToken;
  }

  public String resolveRefreshToken(HttpServletRequest request) {
    LOGGER.info("[resolveToken] 쿠키에서 토큰 값 추출");
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("REFRESH_TOKEN".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
  public boolean validateRefreshToken(String refreshToken) {
    LOGGER.info("[validateRefreshToken] 리프레쉬 토큰 유효 체크 시작");
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(refreshKey)
          .parseClaimsJws(refreshToken)
          .getBody();

      // 리프레쉬 토큰의 만료 시간을 확인
      Date expirationDate = claims.getExpiration();
      Date now = new Date();
      if (expirationDate.before(now)) {
        LOGGER.info("[validateRefreshToken] 리프레쉬 토큰 만료");
        return false;
      }
      LOGGER.info("[validateRefreshToken] 리프레쉬 토큰 유효 체크 완료");
      return true;
    } catch (Exception e) {
      LOGGER.info("[validateRefreshToken] 리프레쉬 토큰 유효 체크 예외 발생");
      return false;
    }
  }

  public String generateAccessTokenFromRefreshToken(String refreshToken) {
    String userId = refreshTokenRepository.findUserIdByRefreshToken(refreshToken);
    if (userId == null) {
      throw new IllegalArgumentException("Invalid refresh token");
    }
    String username = memberRepository.findById(Long.parseLong(userId)).orElseThrow(()->new NoSuchElementException("회원 정보가 존재하지 않습니다.")).getUsername();
    List<String> roles = userDetailsService.loadUserByUsername(username).getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return createToken(username, roles);
  }

  public void addAccessTokenToResponse(String accessToken, HttpServletResponse response) {
    response.setHeader("X-AUTH-TOKEN", accessToken);
  }
}

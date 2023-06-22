package com.example.MunDeuk.repository;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

  private final RedisTemplate<String, String> redisTemplate;

  public RefreshTokenRepository(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void saveRefreshToken(String refreshToken, Long userId, long expiration) {
    String key = String.valueOf(userId); // 사용자 ID를 포함한 키 생성
    redisTemplate.opsForValue().set(key, refreshToken, expiration, TimeUnit.MILLISECONDS);
  }

  public void deleteRefreshToken(String refreshToken) {
    redisTemplate.delete(refreshToken);
  }

  public boolean isRefreshTokenExists(Long userId) {
    String key = String.valueOf(userId); // 사용자 ID를 키로 사용
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));  // redis에서 null을 반환할 때 false 반환 처리하는 코드
  }

  public String findRefreshTokenByUserId(Long userId) {
    for (String refreshToken : Objects.requireNonNull(redisTemplate.keys("*"),"DB에 해당 리프레쉬 토큰이 존재하지 않습니다.")) {
      String storedUserId = redisTemplate.opsForValue().get(refreshToken);
      if (String.valueOf(userId).equals(storedUserId)) {
        return refreshToken;
      }
    }
    return null;
  }
  public String findUserIdByRefreshToken(String refreshToken) {
    Set<String> keys = redisTemplate.keys("*"); // 모든 키 조회
    for (String key : keys) {
      String storedRefreshToken = redisTemplate.opsForValue().get(key);
      if (refreshToken.equals(storedRefreshToken)) {
        // 레디스 키에서 userId 추출
        return key;
      }
    }
    return null; // 해당 refreshToken에 대한 userId가 없을 경우
  }

}
package com.example.MunDeuk.global.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
  /* 400 BAD_REQUEST : 잘못된 요청 */
  /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
  INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

  INVALID_AUTH_TASK(HttpStatus.NOT_ACCEPTABLE, "로그인 시 사용가능한 기능입니다."),

  /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),

  /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
  DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),

  FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 게시글을 찾을 수 없습니다."),

  LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "위치정보를 찾을 수 없습니다."),
  ;


  private final HttpStatus httpStatus;
  private final String message;

}

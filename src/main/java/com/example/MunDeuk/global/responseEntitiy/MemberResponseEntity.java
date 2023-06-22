package com.example.MunDeuk.global.responseEntitiy;

import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@RequiredArgsConstructor
public class MemberResponseEntity<T> {

  private final HttpStatus httpStatus;
  private final T body;
  private final String msg;


  public static <T> MemberResponseEntity<T> sucess(T body) {
    return new MemberResponseEntity<>(HttpStatus.OK, body,"성공");
  }

  public static <T> MemberResponseEntity<T> fail(MunDeukRuntimeException e) {
    return new MemberResponseEntity<>(e.getErrorCode().getHttpStatus(), null, e.getErrorCode().getMessage());
  }

  public static <T> MemberResponseEntity<T> error(HttpStatus status, String message) {
    return new MemberResponseEntity<>(status, null, message);
  }


}

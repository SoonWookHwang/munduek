package com.example.MunDeuk.global.responseEntitiy;

import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@Component
public class MemberResponseEntity<T> {
  private HttpStatus httpStatus;
  private T body;
  private String msg;

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

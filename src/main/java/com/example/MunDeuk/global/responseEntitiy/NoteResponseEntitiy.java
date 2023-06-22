package com.example.MunDeuk.global.responseEntitiy;

import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@RequiredArgsConstructor
public class NoteResponseEntitiy<T> {

  private final HttpStatus httpStatus;
  private final T body;
  private final String msg;

  public static <T> NoteResponseEntitiy<T> sucess(T body) {
    return new NoteResponseEntitiy<>(HttpStatus.OK, body,"성공");
  }
  public static <T> NoteResponseEntitiy<T> fail(MunDeukRuntimeException e) {
    return new NoteResponseEntitiy<>(e.getErrorCode().getHttpStatus(), null, e.getErrorCode().getMessage());
  }

  public static <T> NoteResponseEntitiy<T> error(HttpStatus status, String message) {
    return new NoteResponseEntitiy<>(status, null, message);
  }

//  @Builder
//  public static <T> FeedResponseEntitiy<T> builder(HttpStatus httpStatus,
//      MultiValueMap<String, String> headers, T body, String msg) {
//    FeedResponseEntitiy<T> responseEntitiy = new FeedResponseEntitiy<>();
//    responseEntitiy.httpStatus = httpStatus;
//    responseEntitiy.headers = headers;
//    responseEntitiy.body = body;
//    responseEntitiy.msg = msg;
//
//    return responseEntitiy;
//  }
//
//  @Builder
//  public static <T> FeedResponseEntitiy<T> builder(HttpStatus httpStatus, T body, String msg) {
//    FeedResponseEntitiy<T> responseEntitiy = new FeedResponseEntitiy<>();
//    responseEntitiy.httpStatus = httpStatus;
//    responseEntitiy.body = body;
//    responseEntitiy.msg = msg;
//
//    return responseEntitiy;
//  }

}

package com.example.MunDeuk.global.responseEntitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Setter
@Getter
@RequiredArgsConstructor
@Component
public class NoteResponseEntitiy<T> {

  private HttpStatus httpStatus;
  private T body;
  private String msg;

  public NoteResponseEntitiy<T> sucess(T body) {
    this.httpStatus = HttpStatus.OK;
    this.msg = "정상";
    this.body = body;
    return this;
  }

  public NoteResponseEntitiy<T> error(Exception e, HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    this.msg = e.getMessage();
    return this;
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

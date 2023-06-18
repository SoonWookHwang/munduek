package com.example.MunDeuk.global.errors;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


@Data
public class ErrorResponse{

  private int httpcode;

  private String errorMsg;


  @Builder
  public ErrorResponse(MunDeukRuntimeException exception){
    this.httpcode = exception.getErrorCode().getHttpStatus().value();
    this.errorMsg = exception.getMessage();
  }

  public static ResponseEntity<String> createResponseEntity(ErrorResponse error){
    return new ResponseEntity<>(error.getErrorMsg(),HttpStatusCode.valueOf(error.httpcode));

  }


}
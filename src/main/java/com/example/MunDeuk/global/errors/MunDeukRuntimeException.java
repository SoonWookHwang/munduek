package com.example.MunDeuk.global.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class MunDeukRuntimeException extends RuntimeException {

  private final CustomErrorCode errorCode;

  public MunDeukRuntimeException(CustomErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public CustomErrorCode getErrorCode() {
    return errorCode;
  }

  @ExceptionHandler(MunDeukRuntimeException.class)
  public ResponseEntity<String> handleCustomException(MunDeukRuntimeException e) {
    ErrorResponse error = ErrorResponse.builder().exception(e).build();
    return ErrorResponse.createResponseEntity(error);
  }
}

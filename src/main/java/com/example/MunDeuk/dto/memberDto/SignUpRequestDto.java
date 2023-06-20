package com.example.MunDeuk.dto.memberDto;

import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

  private String username;
  private String password;
  private String passwordCheck;


  public boolean validInputValue(String username, String password) {
    Pattern usernamePattern = Pattern.compile("^[a-z0-9]+$");
    if (!usernamePattern.matcher(username).matches()) {
      return false;
    }
    Pattern passwordPattern = Pattern.compile(
        "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{8,20}$");
    if (!passwordPattern.matcher(password).matches()) {
      return false;
    }
    return true;
  }

}

package com.example.MunDeuk.controller;

import com.example.MunDeuk.dto.memberDto.LoginRequestDto;
import com.example.MunDeuk.dto.memberDto.MemberDetailsReqeustDto;
import com.example.MunDeuk.dto.memberDto.SignUpRequestDto;
import com.example.MunDeuk.dto.memberDto.TokenDto;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.global.responseEntitiy.MemberResponseEntity;
import com.example.MunDeuk.service.AuthService;
import com.example.MunDeuk.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final MemberService memberService;
  private final AuthService authService;

  @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public MemberResponseEntity<?> signUpMember(@ModelAttribute SignUpRequestDto dto) {
    log.info("[MemberController.signupMember] api 진입");
    log.info("유저네임=" + dto.getUsername());
    try {
      //수행작업
      log.info("[MemberController.signupMember] memberSerice signupMember 메서드 호출");
      return MemberResponseEntity.sucess(memberService.signUpMember(dto));
    } catch (MunDeukRuntimeException e) {
      //예상된 에러
      return MemberResponseEntity.fail(e);
    } catch (Exception e) {
      //예상치 못한 에러
      return MemberResponseEntity.error(HttpStatus.CONFLICT, e.getMessage());
    }
  }

  @PutMapping
  public MemberResponseEntity<?> updateMemberDetails(Long detailsId, MemberDetailsReqeustDto dto) {
    return MemberResponseEntity.sucess(memberService.updateMemberDetails(detailsId, dto));
  }

  @GetMapping
  public MemberResponseEntity<?> getMember(Long memberId) {
    return MemberResponseEntity.sucess(memberService.getMember(memberId));
  }

  @GetMapping("/details")
  public MemberResponseEntity<?> getMemberDetails(Long memberId) {
    try {
      return MemberResponseEntity.sucess(memberService.getMember(memberId));
    } catch (MunDeukRuntimeException e) {
      return MemberResponseEntity.fail(e);
    } catch (Exception e) {
      return MemberResponseEntity.error(HttpStatus.CONFLICT, e.getMessage());
    }
  }

  @PostMapping("/login")
  public String login(LoginRequestDto dto, HttpServletResponse response){
    TokenDto tokenDto = authService.login(dto,response);
    String accessToken = tokenDto.getAccessToken();
    response.setHeader("X-AUTH-TOKEN", accessToken);
    return "redirect:index";
  }

}

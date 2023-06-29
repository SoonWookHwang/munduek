package com.example.MunDeuk.service;

import com.example.MunDeuk.dto.memberDto.MemberDetailsReqeustDto;
import com.example.MunDeuk.dto.memberDto.SignUpRequestDto;
import com.example.MunDeuk.global.errors.CustomErrorCode;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.models.postgres.Member;
import com.example.MunDeuk.models.postgres.MemberDetails;
import com.example.MunDeuk.repository.postgres.MemberDetailsRepository;
import com.example.MunDeuk.repository.postgres.MemberRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberDetailsRepository memberDetailsRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  private final LockerService lockerService;


  public Member signUpMember(SignUpRequestDto dto) {
    log.info("[MemberService.signupMember] 메서드 진입");
    String username = dto.getUsername();
    String password = dto.getPassword();
    log.info(username);
    log.info(password);
    log.info("[MemberService.signupMember] 아이디,패스워드 추출 ");
    String passwordCheck = dto.getPasswordCheck();
    if(!dto.validInputValue(username, password)){
      throw new MunDeukRuntimeException(CustomErrorCode.ILLEGAL_INPUT_VALUE);
    }
    if(memberRepository.existsByUsername(username)){
      throw new MunDeukRuntimeException(CustomErrorCode.DUPLICATE_ID_EXIST);
    }
    if(!password.equals(passwordCheck)){
      throw new MunDeukRuntimeException(CustomErrorCode.PASSWORD_MISMATCH);
    }
    Member newMember;
    log.info("[MemberService.signupMember] newMember 객체 생성 ");
    if (dto.getRole() != null && dto.getRole().equalsIgnoreCase("admin")) {
      log.info("[MemberService.signupMember] admin entity 생성 ");
      newMember = Member.builder()
          .username(username)
          .password(passwordEncoder.encode(password))
          .roles(Collections.singletonList("ROLE_ADMIN"))
          .memberDetails(createMemberDetails())
//          .memberDetails(null)
          .build();
    } else {
      log.info("[MemberService.signupMember] member entity 생성");
      newMember = Member.builder()
          .username(username)
          .password(passwordEncoder.encode(password))
          .roles(Collections.singletonList("ROLE_USER"))
          .memberDetails(createMemberDetails())
//          .memberDetails(null)
          .build();
    }
    log.info("[MemberService.signupMember] if문 탈출 진입");
    return memberRepository.save(newMember);
  }

  public MemberDetails createMemberDetails() {
    log.info("[MemberService] createMemberDetails 진입");
    MemberDetails newMemberDetails = new MemberDetails(null,null);
    newMemberDetails.setLocker(lockerService.createLocker());
    return memberDetailsRepository.save(newMemberDetails);
  }

  @Transactional
  public MemberDetails updateMemberDetails(Long detailsId, MemberDetailsReqeustDto dto) {
    MemberDetails found = memberDetailsRepository.findById(detailsId)
        .orElseThrow(() -> new MunDeukRuntimeException(CustomErrorCode.USER_NOT_FOUND));
    found.updateMemberDetails(found, dto);
    return found;
  }

  @Transactional(readOnly = true)
  public Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new MunDeukRuntimeException(CustomErrorCode.USER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public MemberDetails getMemberDetails(Long memberId) {
    return memberDetailsRepository.findById(memberId)
        .orElseThrow(() -> new MunDeukRuntimeException(CustomErrorCode.USER_NOT_FOUND));
  }


}

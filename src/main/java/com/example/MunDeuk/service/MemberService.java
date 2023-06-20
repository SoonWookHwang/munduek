package com.example.MunDeuk.service;

import com.example.MunDeuk.dto.memberDto.MemberDetailsReqeustDto;
import com.example.MunDeuk.dto.memberDto.SignUpRequestDto;
import com.example.MunDeuk.global.errors.CustomErrorCode;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.models.Member;
import com.example.MunDeuk.models.MemberDetails;
import com.example.MunDeuk.repository.LockerRepository;
import com.example.MunDeuk.repository.MemberDetailsRepository;
import com.example.MunDeuk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberDetailsRepository memberDetailsRepository;

  private final LockerService lockerService;


  @Transactional
  public Member signUpMember(SignUpRequestDto dto){
    String username = dto.getUsername();
    String password = dto.getPassword();
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
    Member newMember = Member.builder().username(username).password(password).memberDetails(createMemberDetails()).build();
    return memberRepository.save(newMember);
  }

  public MemberDetails createMemberDetails(){
    MemberDetails newMemberDetails = new MemberDetails();
    newMemberDetails.setLocker(newMemberDetails,lockerService.createLocker());
    return memberDetailsRepository.save(newMemberDetails);
  }

  @Transactional
  public MemberDetails updateMemberDetails(Long detailsId, MemberDetailsReqeustDto dto){
    MemberDetails found = memberDetailsRepository.findById(detailsId).orElseThrow(()->new MunDeukRuntimeException(CustomErrorCode.USER_NOT_FOUND));
    found.updateMemberDetails(found,dto);
    return found;
  }

  @Transactional(readOnly = true)
  public Member getMember(Long memberId){
    return memberRepository.findById(memberId).orElseThrow(()->new MunDeukRuntimeException(CustomErrorCode.USER_NOT_FOUND));
  }
  @Transactional(readOnly = true)
  public MemberDetails getMemberDetails(Long memberId){
    return memberDetailsRepository.findById(memberId).orElseThrow(()->new MunDeukRuntimeException(CustomErrorCode.USER_NOT_FOUND));
  }

}

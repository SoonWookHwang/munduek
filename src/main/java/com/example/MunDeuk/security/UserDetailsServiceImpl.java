package com.example.MunDeuk.security;

import com.example.MunDeuk.models.Member;
import com.example.MunDeuk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member user =  memberRepository.findByUsername(username)
        .orElseThrow(()->new UsernameNotFoundException("아이디가 존재하지 않습니다"));
    return new UserDetailsImpl(user);
  }

}

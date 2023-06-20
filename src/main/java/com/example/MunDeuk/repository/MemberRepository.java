package com.example.MunDeuk.repository;

import com.example.MunDeuk.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

  boolean existsByUsername(String username);


}

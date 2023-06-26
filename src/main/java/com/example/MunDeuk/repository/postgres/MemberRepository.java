package com.example.MunDeuk.repository.postgres;

import com.example.MunDeuk.models.postgres.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

  boolean existsByUsername(String username);
  Optional<Member> findByUsername(String username);


}

package com.example.MunDeuk.models.postgres;

import com.example.MunDeuk.dto.memberDto.MemberDetailsReqeustDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "member_details")
@Getter
public class MemberDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String email;

  @Column(nullable = false, unique = true)
  private String nickname;

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "locker_id")
  private Locker locker;


  public void setLocker(MemberDetails target, Locker locker){
    target.locker= locker;
  }


  public void updateMemberDetails(MemberDetails target, MemberDetailsReqeustDto dto){
    target.email = dto.getEmail();
    target.nickname = dto.getNickname();
  }


}

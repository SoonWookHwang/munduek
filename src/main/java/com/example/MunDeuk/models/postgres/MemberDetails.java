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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_details")
@Getter
@NoArgsConstructor
public class MemberDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String email;

  @Column(unique = true)
  private String nickname;

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "locker_id")
  private Locker locker;

  public MemberDetails(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }


  public void setLocker(Locker locker){
    this.locker = locker;
  }




  public void updateMemberDetails(MemberDetails target, MemberDetailsReqeustDto dto){
    target.email = dto.getEmail();
    target.nickname = dto.getNickname();
  }


}

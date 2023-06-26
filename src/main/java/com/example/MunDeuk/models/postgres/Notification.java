package com.example.MunDeuk.models.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String detail;

  @OneToMany
  private List<Member> noticeableId;

  @Column
  private String notificationType;

  @Column
  private String notificationDetail;

  @OneToMany
  private List<Member> notifiedMemberId;

}

package com.example.MunDeuk.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "saved_notes")
public class SavedNote {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;





}

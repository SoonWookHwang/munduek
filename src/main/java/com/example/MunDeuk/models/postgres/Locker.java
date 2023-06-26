package com.example.MunDeuk.models.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "locker")
@Data
public class Locker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "saved_notes")
  private List<Note> savedNotes;

  @OneToMany
  @JoinColumn(name = "viewed_notes")
  private List<Note> viewedNotes;


  public void savedNotesInit(){
    this.savedNotes = new ArrayList<>();
  }



}

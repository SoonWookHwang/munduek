package com.example.MunDeuk.models;

import com.example.MunDeuk.dto.noteDto.ForWriteRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
public class Note {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "contents", length = 500)
  private String content;
  @Column
  private boolean isDeleted;
  @Column
  private Long viewCount;

  @Column
  private int ownCount;

  @Column
  private Double latitude;

  @Column
  private Double longitude;


  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Member ownerId;

  @ManyToOne
  @JoinColumn(name = "origin_id")
  private Member originId;

  @Builder
  public Note(ForWriteRequestDto requestDto) {
    this.content = requestDto.getContent();
    this.viewCount = 0L;
    this.isDeleted = false;
  }

  public void addViewCount(Note note) {
    note.viewCount += 1;
  }

  public void addOwnCount(Note note) {
    note.ownCount += 1;
  }

//  @Column(name = "feed_title")
//  private String title;

//  @Column(name = "image_url")
//  private String image;

//  @Column(name = "soundtrack_url")
//  private String soundtrack;

//  public static Note dtoToEntity(ForWriteRequestDto requestDto){
//    Note newNote = new Note();
//    newNote.title = requestDto.getTitle();
//    newNote.content = requestDto.getContent();
//    newNote.image = requestDto.getImage();
//    newNote.soundtrack = requestDto.getSoundtrack();
//    return newNote;
//  }

}

package com.example.MunDeuk.dto.noteDto;

import com.example.MunDeuk.models.Note;
import lombok.Data;

@Data
public class NoteResponseDto {

  private String title;
  private String contents;
  private String image;
  private String soundtrack;


  public NoteResponseDto(Note note){
    this.title = note.getTitle();
    this.contents = note.getContent();
    this.image = note.getImage();
    this.soundtrack = note.getSoundtrack();

  }

}

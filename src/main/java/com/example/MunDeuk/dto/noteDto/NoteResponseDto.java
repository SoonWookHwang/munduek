package com.example.MunDeuk.dto.noteDto;

import com.example.MunDeuk.models.postgres.MemberDetails;
import com.example.MunDeuk.models.postgres.Note;
import lombok.Data;

@Data
public class NoteResponseDto {

  private String writer;
  private String contents;
//  private String title;
//  private String image;
//  private String soundtrack;


  public NoteResponseDto(Note note, MemberDetails memberDetails){
    this.setWriter(memberDetails.getNickname());
    this.setContents(note.getContent());
  }

}

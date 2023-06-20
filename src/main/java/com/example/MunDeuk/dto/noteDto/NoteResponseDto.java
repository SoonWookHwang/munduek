package com.example.MunDeuk.dto.noteDto;

import com.example.MunDeuk.models.Member;
import com.example.MunDeuk.models.Note;
import lombok.Data;

@Data
public class NoteResponseDto {

  private String writer;
  private String contents;
//  private String title;
//  private String image;
//  private String soundtrack;


  public NoteResponseDto(Note note, Member member){
    this.setWriter(member.getNickname());
    this.setContents(note.getContent());
  }

}

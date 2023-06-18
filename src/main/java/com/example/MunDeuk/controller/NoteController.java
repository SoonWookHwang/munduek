package com.example.MunDeuk.controller;


import com.example.MunDeuk.dto.noteDto.ForWriteRequestDto;
import com.example.MunDeuk.dto.noteDto.NoteResponseDto;
import com.example.MunDeuk.global.responseEntitiy.NoteResponseEntitiy;
import com.example.MunDeuk.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class NoteController {

  private final NoteService noteService;
  private final NoteResponseEntitiy<NoteResponseDto> noteResponseEntitiy;

  @PostMapping("/feeds")
  @ResponseBody
  public NoteResponseEntitiy<?> createFeed(@RequestBody ForWriteRequestDto dto){
      return noteResponseEntitiy.sucess(noteService.createFeed(dto));
  }

}

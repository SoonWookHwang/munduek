package com.example.MunDeuk.service;

import com.example.MunDeuk.dto.noteDto.NoteResponseDto;
import com.example.MunDeuk.dto.noteDto.ForWriteRequestDto;
import com.example.MunDeuk.global.errors.CustomErrorCode;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.models.postgres.MemberDetails;
import com.example.MunDeuk.models.postgres.Note;
import com.example.MunDeuk.repository.postgres.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

  private final NoteRepository noteRepository;

  @Transactional
  public NoteResponseDto createFeed(ForWriteRequestDto dto) {
    Note note = Note.builder().requestDto(dto).build();
    MemberDetails writer = new MemberDetails();
    return new NoteResponseDto(noteRepository.save(note),writer);
  }

  @Transactional(readOnly = true)
  public NoteResponseDto readFeed(Long noteId) {
    Note found = noteRepository.findById(noteId)
        .orElseThrow(() -> new MunDeukRuntimeException(CustomErrorCode.NOTE_NOT_FOUND));
    MemberDetails writer = new MemberDetails();
    return new NoteResponseDto(found,writer);
  }

}

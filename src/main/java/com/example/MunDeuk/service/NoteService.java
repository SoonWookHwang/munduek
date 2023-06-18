package com.example.MunDeuk.service;

import com.example.MunDeuk.dto.noteDto.NoteResponseDto;
import com.example.MunDeuk.dto.noteDto.ForWriteRequestDto;
import com.example.MunDeuk.global.errors.CustomErrorCode;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.models.Note;
import com.example.MunDeuk.repository.NoteRepository;
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
    return new NoteResponseDto(noteRepository.save(note));
  }

  @Transactional(readOnly = true)
  public NoteResponseDto readFeed(Long noteId) {
    Note found = noteRepository.findById(noteId)
        .orElseThrow(() -> new MunDeukRuntimeException(CustomErrorCode.FEED_NOT_FOUND));
    return new NoteResponseDto(found);
  }

}

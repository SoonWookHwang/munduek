package com.example.MunDeuk.service;

import com.example.MunDeuk.global.errors.CustomErrorCode;
import com.example.MunDeuk.global.errors.MunDeukRuntimeException;
import com.example.MunDeuk.models.Locker;
import com.example.MunDeuk.models.Member;
import com.example.MunDeuk.models.Note;
import com.example.MunDeuk.repository.LockerRepository;
import com.example.MunDeuk.repository.MemberRepository;
import com.example.MunDeuk.repository.NoteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LockerService {

  private final LockerRepository lockerRepository;
  private final MemberRepository memberRepository;
  private final NoteRepository noteRepository;


  public Locker createLocker(){
    Locker newLocker = new Locker();
    newLocker.savedNotesInit();
    return lockerRepository.save(newLocker);
  }


  @Transactional
  public List<Note> saveNoteToLocker(Long noteId, Long lockerId){
    Note target = noteRepository.findById(noteId).orElseThrow(()->new MunDeukRuntimeException(
        CustomErrorCode.NOTE_NOT_FOUND));
    Locker locker = lockerRepository.findById(lockerId)
        .orElseThrow(() -> new MunDeukRuntimeException(CustomErrorCode.ILLEGAL_INPUT_VALUE));
    List<Note> list = locker.getSavedNotes();
    list.add(target);
    return list;
  }
}

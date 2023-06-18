package com.example.MunDeuk.repository;

import com.example.MunDeuk.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {

}

package com.example.MunDeuk.repository.postgres;

import com.example.MunDeuk.models.postgres.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {

}

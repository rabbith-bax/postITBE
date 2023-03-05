package com.postIT.postIT.repository;

import com.postIT.postIT.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note, Long> {

    void deleteNoteById(Long id);
}

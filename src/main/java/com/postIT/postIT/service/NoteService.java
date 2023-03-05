package com.postIT.postIT.service;

import com.postIT.postIT.model.Note;
import com.postIT.postIT.repository.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepo noteRepo;

    @Autowired
    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public Note addNote(Note note) {
        return noteRepo.save(note);
    }

    public List<Note> findAllNotes() {
        return noteRepo.findAll();
    }

    public Note updateNote(Note note) {
        return noteRepo.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepo.deleteNoteById(id);
    }
}

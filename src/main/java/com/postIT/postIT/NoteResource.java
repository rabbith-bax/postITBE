package com.postIT.postIT;

import com.postIT.postIT.model.Note;
import com.postIT.postIT.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteResource {
    private final NoteService noteService;

    public NoteResource(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.findAllNotes();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note newNote = noteService.addNote(note);
        return new ResponseEntity<>(newNote, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        Note updateNote = noteService.updateNote(note);
        return new ResponseEntity<>(updateNote, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("id") Long id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

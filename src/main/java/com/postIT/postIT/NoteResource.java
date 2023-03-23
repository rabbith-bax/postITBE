package com.postIT.postIT;

import com.postIT.postIT.model.Note;
import com.postIT.postIT.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        checkLengthOfANote(note);
        Note newNote = noteService.addNote(note);
        return new ResponseEntity<>(newNote, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        checkLengthOfANote(note);
        Note updateNote = noteService.updateNote(note);
        return new ResponseEntity<>(updateNote, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteNote(@PathVariable("id") Long id) {
        if (checkIfNotesExists(id)) {
            noteService.deleteNoteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("Note with this id doesn't exist");
    }

    private boolean checkIfNotesExists(Long id) {
        List<Long> notesId = noteService
                .findAllNotes()
                .stream()
                .map(Note::getId)
                .toList();

        return notesId.contains(id);
    }

    private void checkLengthOfANote(Note note) {
        if (note.getContent().length() > 200) {
            throw new NoteContentExceedsSizeLimitException("Size of note content cannot exceed 200 characters");
        }
    }

    @ExceptionHandler(NoteContentExceedsSizeLimitException.class)
    public ResponseEntity<String> handleNoteContentExceedsSizeLimitException(NoteContentExceedsSizeLimitException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public static class NoteContentExceedsSizeLimitException extends RuntimeException {
        public NoteContentExceedsSizeLimitException(String message) {
            super(message);
        }
    }
}

package com.postIT.postIT;

import com.postIT.postIT.model.Note;
import com.postIT.postIT.repository.NoteRepo;
import com.postIT.postIT.service.NoteService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class NoteServiceTest {

    @Mock
    private NoteRepo noteRepo;

    @InjectMocks
    private NoteService noteService;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddNote() {
        Note note = new Note();
        when(noteRepo.save(note)).thenReturn(note);

        Note result = noteService.addNote(note);
        verify(noteRepo, times(1)).save(note);
        assertEquals(result, note);
    }

    @Test
    public void testFindAllNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note());
        notes.add(new Note());

        when(noteRepo.findAll()).thenReturn(notes);

        List<Note> result = noteService.findAllNotes();
        verify(noteRepo, times(1)).findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    public void testUpdateNote() {
        Note note = new Note();
        when(noteRepo.save(note)).thenReturn(note);

        Note result = noteService.updateNote(note);
        verify(noteRepo, times(1)).save(note);
        assertEquals(result, note);
    }

    @Test
    public void testDeleteNoteById() {
        Long id = 1L;
        doNothing().when(noteRepo).deleteNoteById(id);

        noteService.deleteNoteById(id);
        verify(noteRepo, times(1)).deleteNoteById(id);
    }
}
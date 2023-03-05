package com.postIT.postIT;

import com.postIT.postIT.model.Note;
import com.postIT.postIT.service.NoteService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest
public class NoteResourceTest {
    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteResource noteResource;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1L, "Test Note 1"));
        notes.add(new Note(2L, "Test Note 2"));

        when(noteService.findAllNotes()).thenReturn(notes);

        ResponseEntity<List<Note>> responseEntity = noteResource.getAllNotes();
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        List<Note> responseNotes = responseEntity.getBody();
        assertNotNull(responseNotes);
        assertEquals(responseNotes.size(), notes.size());

        for (int i = 0; i < responseNotes.size(); i++) {
            assertEquals(responseNotes.get(i).getId(), notes.get(i).getId());
            assertEquals(responseNotes.get(i).getContent(), notes.get(i).getContent());
        }
    }

    @Test
    public void testAddNote() {
        Note note = new Note();
        note.setContent("Test Note");

        when(noteService.addNote(note)).thenReturn(note);

        ResponseEntity<Note> responseEntity = noteResource.addNote(note);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);

        Note responseNote = responseEntity.getBody();
        assertNotNull(responseNote);
        assertEquals(responseNote.getContent(), note.getContent());
    }

    @Test
    public void testUpdateNote() {
        Note note = new Note();
        note.setId(1L);
        note.setContent("Test Note");

        when(noteService.updateNote(note)).thenReturn(note);

        ResponseEntity<Note> responseEntity = noteResource.updateNote(note);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        Note responseNote = responseEntity.getBody();
        assertNotNull(responseNote);
        assertEquals(responseNote.getId(), note.getId());
        assertEquals(responseNote.getContent(), note.getContent());
    }

    @Test
    public void testDeleteNote() {
        ResponseEntity<?> responseEntity = noteResource.deleteNote(1L);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}

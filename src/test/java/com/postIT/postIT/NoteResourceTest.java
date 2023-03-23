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

@SpringBootTest
public class NoteResourceTest {
    @InjectMocks
    private NoteResource noteResource;

    @Mock
    private NoteService noteService;

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
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), notes);
    }

    @Test
    public void testAddNote() {
        Note note = new Note();
        note.setContent("Test Note");

        when(noteService.addNote(note)).thenReturn(note);

        ResponseEntity<Note> responseEntity = noteResource.addNote(note);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody().getContent(), note.getContent());
    }

    @Test(expectedExceptions = NoteResource.NoteContentExceedsSizeLimitException.class)
    public void testAddNoteWithExceededContentLength() {
        Note note = new Note();
        note.setContent("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. " +
                "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. " +
                "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis " +
                "enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, " +
                "imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. " +
                "Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleife");

        noteResource.addNote(note);
    }

    @Test
    public void testHandleNoteContentExceedsSizeLimitException() {
        String errorMessage = "Size of note content cannot exceed 200 characters";
        NoteResource.NoteContentExceedsSizeLimitException e = new NoteResource
                .NoteContentExceedsSizeLimitException(errorMessage);

        ResponseEntity<String> response = noteResource.handleNoteContentExceedsSizeLimitException(e);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), errorMessage);
    }

    @Test
    public void testUpdateNote() {
        Note note = new Note();
        note.setId(1L);
        note.setContent("Test Note");

        when(noteService.updateNote(note)).thenReturn(note);

        ResponseEntity<Note> responseEntity = noteResource.updateNote(note);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getId(), note.getId());
        assertEquals(responseEntity.getBody().getContent(), note.getContent());
    }

    @Test
    public void testDeleteNoteWithValidId() {
        Long noteId = 1L;
        Note note = new Note();
        note.setId(noteId);
        when(noteService.findAllNotes()).thenReturn(List.of(note));

        ResponseEntity<?> response = noteResource.deleteNote(noteId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testDeleteNoteWithInvalidId() {
        Long noteId = 2L;
        Note note = new Note();
        note.setId(1L);
        when(noteService.findAllNotes()).thenReturn(List.of(note));

        ResponseEntity<?> response = noteResource.deleteNote(noteId);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), "Note with this id doesn't exist");
    }

    @Test
    public void testDeleteNoteWhenNoNotesExist() {
        Long noteId = 1L;
        when(noteService.findAllNotes()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = noteResource.deleteNote(noteId);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), "Note with this id doesn't exist");
    }
}

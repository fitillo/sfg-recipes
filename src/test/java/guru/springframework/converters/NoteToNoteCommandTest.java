package guru.springframework.converters;

import guru.springframework.commands.NoteCommand;
import guru.springframework.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteToNoteCommandTest {

    public static final long ID = 1L;
    public static final String NOTE = "note";
    private NoteToNoteCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NoteToNoteCommand();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(Note.builder().build()));
    }

    @Test
    void convert() {
        NoteCommand note = converter.convert(Note.builder().id(ID).notes(NOTE).build());
        assertNotNull(note);
        assertEquals(note.getId(), ID);
        assertEquals(note.getNotes(), NOTE);
    }
}
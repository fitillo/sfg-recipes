package guru.springframework.converters;

import guru.springframework.commands.NoteCommand;
import guru.springframework.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandToNoteTest {

    public static final long ID = 1L;
    public static final String NOTE = "note";
    private NoteCommandToNote converter;

    @BeforeEach
    void setUp() {
        converter = new NoteCommandToNote();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(NoteCommand.builder().build()));
    }

    @Test
    void convert() {
        Note note = converter.convert(NoteCommand.builder().id(ID).notes(NOTE).build());
        assertNotNull(note);
        assertEquals(note.getId(), ID);
        assertEquals(note.getNotes(), NOTE);
        assertNull(note.getRecipe());
    }
}
package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteToNotesCommandTest {

    public static final long ID = 1L;
    public static final String NOTE = "note";
    private NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(Notes.builder().build()));
    }

    @Test
    void convert() {
        NotesCommand note = converter.convert(Notes.builder().id(ID).recipeNotes(NOTE).build());
        assertNotNull(note);
        assertEquals(note.getId(), ID);
        assertEquals(note.getRecipeNotes(), NOTE);
    }
}
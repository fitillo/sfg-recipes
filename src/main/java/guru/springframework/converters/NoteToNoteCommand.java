package guru.springframework.converters;

import guru.springframework.commands.NoteCommand;
import guru.springframework.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {
    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(Note source) {
        return (source == null) ? null :
                NoteCommand.builder().id(source.getId()).notes(source.getNotes()).build();
    }
}

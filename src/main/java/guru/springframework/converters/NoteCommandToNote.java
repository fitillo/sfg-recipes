package guru.springframework.converters;

import guru.springframework.commands.NoteCommand;
import guru.springframework.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {
    @Synchronized
    @Nullable
    @Override
    public Note convert(NoteCommand source) {
        return (source == null) ? null :
                Note.builder().id(source.getId()).notes(source.getNotes()).build();
    }
}

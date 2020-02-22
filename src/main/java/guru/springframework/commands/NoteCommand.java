package guru.springframework.commands;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteCommand {

    private Long id;
    private String notes;
}

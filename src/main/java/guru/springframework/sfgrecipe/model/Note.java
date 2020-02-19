package guru.springframework.sfgrecipe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @EqualsAndHashCode.Exclude
    private Recipe recipe;

    @Lob
    private String notes;
}

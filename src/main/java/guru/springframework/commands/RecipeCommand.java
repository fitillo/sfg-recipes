package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255, message = "Required (under 255)")
    private String description;

    @Positive
    private Integer prepTime;
    @Positive
    private Integer cookTime;
    @Positive
    private Integer servings;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

    @Builder
    public RecipeCommand(Long id, String description, Integer prepTime, Integer cookTime, Integer servings, String source, String url, String directions, Difficulty difficulty, NotesCommand notes) {
        this.id = id;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        this.difficulty = difficulty;
        this.notes = notes;
    }
}

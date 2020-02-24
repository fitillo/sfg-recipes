package guru.springframework.commands;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientCommand {

    private Long id;
    private Long recipeId;
    private String name;
    private Double amount;
    private String suggestion;
    private UnitOfMeasureCommand uom;
}

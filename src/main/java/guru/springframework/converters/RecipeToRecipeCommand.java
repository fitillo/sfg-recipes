package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final NotesToNotesCommand toNoteCommand;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final CategoryToCategoryCommand toCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand toNoteCommand, IngredientToIngredientCommand toIngredientCommand,
                                 CategoryToCategoryCommand toCategoryCommand) {
        this.toNoteCommand = toNoteCommand;
        this.toIngredientCommand = toIngredientCommand;
        this.toCategoryCommand = toCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = RecipeCommand.builder().id(source.getId()).description(source.getDescription())
                .prepTime(source.getPrepTime()).cookTime(source.getCookTime()).servings(source.getServings())
                .source(source.getSource()).url(source.getUrl()).directions(source.getDirections())
                .difficulty(source.getDifficulty())
                .build();

        recipeCommand.setImage(source.getImage());
        recipeCommand.setNotes(toNoteCommand.convert(source.getNotes()));

        if (source.getIngredients() != null) {
            source.getIngredients().forEach(
                    ingredient -> recipeCommand.getIngredients().add(toIngredientCommand.convert(ingredient)));
        }

        if (source.getCategories() != null) {
            source.getCategories().forEach(
                    category -> recipeCommand.getCategories().add(toCategoryCommand.convert(category)));
        }

        return recipeCommand;
    }
}

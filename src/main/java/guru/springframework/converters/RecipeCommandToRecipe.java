package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final NoteCommandToNote noteConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final CategoryCommandToCategory categoryConverter;

    public RecipeCommandToRecipe(NoteCommandToNote noteConverter, IngredientCommandToIngredient ingredientConverter,
                                 CategoryCommandToCategory categoryConverter) {
        this.noteConverter = noteConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = Recipe.builder().id(source.getId()).description(source.getDescription())
                .prepTime(source.getPrepTime()).cookTime(source.getCookTime()).servings(source.getServings())
                .source(source.getSource()).url(source.getUrl()).directions(source.getDirections())
                .difficulty(source.getDifficulty())
                .build();

        recipe.setNotes(noteConverter.convert(source.getNotes()));

        if (source.getIngredients() != null) {
            source.getIngredients().forEach(
                    ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        if (source.getCategories() != null) {
            source.getCategories().forEach(
                    category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        return recipe;
    }
}

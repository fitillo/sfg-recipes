package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NoteCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final long ID = 1L;
    public static final String DESCRIPTION = "description";
    public static final int PREP_TIME = 10;
    public static final Difficulty EASY = Difficulty.EASY;
    public static final double AMOUNT = 2D;
    private static final int COOK_TIME = 10;
    public static final int SERVINGS = 5;
    public static final String SOURCE = "source";
    public static final String URL = "url";
    public static final String DIRECTIONS = "directions";
    public static final String MEXICAN = "Mexican";
    public static final String ITALIAN = "Italian";
    private RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NoteCommandToNote(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory());
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(RecipeCommand.builder().build()));
    }

    @Test
    void convert() {
        RecipeCommand command = RecipeCommand.builder().id(ID).description(DESCRIPTION).prepTime(PREP_TIME)
                .cookTime(COOK_TIME).servings(SERVINGS).source(SOURCE).url(URL).directions(DIRECTIONS).difficulty(EASY)
                .notes(NoteCommand.builder().id(ID).notes(DESCRIPTION).build()).build();

        Set<IngredientCommand> ingredients = new HashSet<>();
        ingredients.add(IngredientCommand.builder().id(ID).amount(AMOUNT).build());
        ingredients.add(IngredientCommand.builder().id(ID+1).amount(AMOUNT).build());

        Set<CategoryCommand> categories = new HashSet<>();
        categories.add(CategoryCommand.builder().id(ID).name(MEXICAN).build());
        categories.add(CategoryCommand.builder().id(ID+1).name(ITALIAN).build());

        command.setCategories(categories);
        command.setIngredients(ingredients);

        Recipe recipe = converter.convert(command);
        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(EASY, recipe.getDifficulty());
        assertEquals(ID, recipe.getNotes().getId());
        assertEquals(DESCRIPTION, recipe.getNotes().getNotes());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getAmount() == AMOUNT).count());
        List<String> categoryStrings = recipe.getCategories().stream().map(Category::getName)
                .collect(Collectors.toList());
        assertTrue(categoryStrings.contains(MEXICAN));
        assertTrue(categoryStrings.contains(ITALIAN));
    }
}
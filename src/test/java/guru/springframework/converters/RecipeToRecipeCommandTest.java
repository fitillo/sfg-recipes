package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

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
    private RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NoteToNoteCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(Recipe.builder().build()));
    }

    @Test
    void convert() {
        Recipe originRecipe = Recipe.builder().id(ID).description(DESCRIPTION).prepTime(PREP_TIME)
                .cookTime(COOK_TIME).servings(SERVINGS).source(SOURCE).url(URL).directions(DIRECTIONS).difficulty(EASY)
                .notes(Note.builder().id(ID).notes(DESCRIPTION).build()).build();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id(ID).amount(AMOUNT).build());
        ingredients.add(Ingredient.builder().id(ID+1).amount(AMOUNT).build());

        Set<Category> categories = new HashSet<>();
        categories.add(Category.builder().id(ID).name(MEXICAN).build());
        categories.add(Category.builder().id(ID+1).name(ITALIAN).build());

        originRecipe.setCategories(categories);
        originRecipe.setIngredients(ingredients);

        RecipeCommand command = converter.convert(originRecipe);
        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(EASY, command.getDifficulty());
        assertEquals(ID, command.getNotes().getId());
        assertEquals(DESCRIPTION, command.getNotes().getNotes());
        assertEquals(2, command.getIngredients().size());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().stream()
                .filter(ingredient -> ingredient.getAmount() == AMOUNT).count());
        List<String> categoryStrings = command.getCategories().stream().map(CategoryCommand::getName)
                .collect(Collectors.toList());
        assertTrue(categoryStrings.contains(MEXICAN));
        assertTrue(categoryStrings.contains(ITALIAN));
    }
}
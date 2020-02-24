package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    public static final long ID = 1L;
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final String AVOCADO = "avocado";
    public static final String MY_RECIPE = "My recipe";

    @InjectMocks
    private IngredientServiceImpl service;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UnitOfMeasureRepository uomRepository;

    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;

    IngredientServiceImplTest() {
        this.toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        service = new IngredientServiceImpl(recipeRepository, uomRepository, toIngredientCommand, toIngredient);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = Recipe.builder().id(ID).build();
        Ingredient ingredient = Ingredient.builder().id(ID).description(AVOCADO).recipe(recipe).build();
        recipe.addIngredient(ingredient);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //then
        IngredientCommand command = service.findByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId());

        assertEquals(ingredient.getId(), command.getId());
        assertEquals(ingredient.getDescription(), command.getDescription());
        assertEquals(ID, command.getRecipeId());
    }

    @Test
    void findByRecipeIdAndNoIngredientId() {
        //given
        Recipe recipe = Recipe.builder().id(ID).build();
        recipe.addIngredient(Ingredient.builder().id(ID).build());

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //then
        assertThrows(RuntimeException.class, () -> service.findByRecipeIdAndIngredientId(recipe.getId(), ID+1));
    }

    @Test
    void testFindNoRecipeIdAndIngredientId() {
        assertThrows(RuntimeException.class, () -> service.findByRecipeIdAndIngredientId(anyLong(), anyLong()));
    }

    @Test
    void testSaveNewIngredientCommand() {
        //given
        IngredientCommand command = IngredientCommand.builder().description(AVOCADO)
                .amount(AMOUNT).recipeId(ID).uom(UnitOfMeasureCommand.builder().id(ID).build()).build();

        Optional<Recipe> recipeOptional = Optional.of(Recipe.builder().id(ID).description(MY_RECIPE).build());
        Recipe savedRecipe = Recipe.builder().id(ID).description(MY_RECIPE).build();
        Ingredient savedIngredient = toIngredient.convert(command);
        savedIngredient.setId(ID);
        savedRecipe.addIngredient(savedIngredient);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedIngredientCommand = service.saveIngredientCommand(command);

        //then
        assertEquals(1, savedRecipe.getIngredients().size());
        assertEquals(ID, savedIngredientCommand.getId());
        assertEquals(AVOCADO, savedIngredientCommand.getDescription());
    }

    @Test
    void testSaveExistingIngredientCommand() {
        //given
        UnitOfMeasureCommand uomCommand = UnitOfMeasureCommand.builder().id(ID).build();
        IngredientCommand command = IngredientCommand.builder().id(ID).description(AVOCADO)
                .uom(uomCommand).amount(new BigDecimal(1)).recipeId(ID).build();


        Recipe recipe = Recipe.builder().id(ID).description(MY_RECIPE).build();
        UnitOfMeasure uom = UnitOfMeasure.builder().id(ID).build();
        Optional<UnitOfMeasure> optionalUOM = Optional.of(uom);
        recipe.addIngredient(Ingredient.builder().id(ID).description("Other name").amount(new BigDecimal(100)).uom(uom).build());
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Recipe savedRecipe = Recipe.builder().id(ID).description(MY_RECIPE).build();
        savedRecipe.addIngredient(toIngredient.convert(command));

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        when(uomRepository.findById(any())).thenReturn(optionalUOM);

        //when
        IngredientCommand savedIngredientCommand = service.saveIngredientCommand(command);

        //then
        assertEquals(1, savedRecipe.getIngredients().size());
        assertEquals(ID, savedIngredientCommand.getId());
        assertEquals(AVOCADO, savedIngredientCommand.getDescription());
        assertEquals(new BigDecimal(1), savedIngredientCommand.getAmount());
    }
}
package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    public static final long ID = 1L;
    public static final String AVOCADO = "avocado";
    @InjectMocks
    private IngredientServiceImpl service;

    @Mock
    private RecipeRepository repository;

    private final IngredientToIngredientCommand toIngredientCommand;

    IngredientServiceImplTest() {
        this.toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() {
        service = new IngredientServiceImpl(repository, toIngredientCommand);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = Recipe.builder().id(ID).build();
        Ingredient ingredient = Ingredient.builder().id(ID).name(AVOCADO).recipe(recipe).build();
        recipe.addIngredient(ingredient);

        //when
        when(repository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //then
        IngredientCommand command = service.findByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId());

        assertEquals(ingredient.getId(), command.getId());
        assertEquals(ingredient.getName(), command.getName());
        assertEquals(ID, command.getRecipeId());
    }

    @Test
    void findByRecipeIdAndNoIngredientId() {
        //given
        Recipe recipe = Recipe.builder().id(ID).build();
        recipe.addIngredient(Ingredient.builder().id(ID).build());

        //when
        when(repository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //then
        assertThrows(RuntimeException.class, () -> service.findByRecipeIdAndIngredientId(recipe.getId(), ID+1));
    }

    @Test
    void testFindNoRecipeIdAndIngredientId() {
        assertThrows(RuntimeException.class, () -> service.findByRecipeIdAndIngredientId(anyLong(), anyLong()));
    }
}
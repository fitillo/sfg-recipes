package guru.springframework.sfgrecipe.services;

import guru.springframework.sfgrecipe.model.Recipe;
import guru.springframework.sfgrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        when(recipeRepository.findAll()).thenReturn(recipes);

        assertTrue(recipeService.getRecipes().isEmpty());

        recipes.add(new Recipe());
        assertEquals(recipeService.getRecipes().size(), 1);

        recipes.add(new Recipe());
        assertEquals(recipeService.getRecipes().size(), 2);

        verify(recipeRepository, times(3)).findAll();
    }
}
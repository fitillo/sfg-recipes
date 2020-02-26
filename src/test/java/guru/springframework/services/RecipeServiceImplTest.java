package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl service;

    @Mock
    private RecipeRepository repository;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.initMocks(this);
        //this.service = new RecipeServiceImpl(repository);
    }

    @Test
    void getRecipesTest() {
        Set<Recipe> recipes = new HashSet<>();
        when(repository.findAll()).thenReturn(recipes);

        assertTrue(service.getRecipes().isEmpty());

        recipes.add(new Recipe());
        assertEquals(service.getRecipes().size(), 1);

        recipes.add(new Recipe());
        assertEquals(service.getRecipes().size(), 2);

        verify(repository, times(3)).findAll();
    }

    @Test
    void getRecipeByIdTest() {
        long guacamoleId = 1L;
        Recipe guacamole = Recipe.builder().id(guacamoleId).description("Guacamole").prepTime(10).cookTime(20).
                servings(5).source("Simply Recipes").build();

        when(repository.findById(guacamoleId)).thenReturn(Optional.of(guacamole));
        assertEquals(guacamoleId, service.findById(guacamoleId).getId());
        verify(repository, times(1)).findById(guacamoleId);
    }

    @Test
    void getRecipeByIdTestNotFound() {
        Optional<Recipe> noRecipe = Optional.empty();
        when(repository.findById(anyLong())).thenReturn(noRecipe);

        assertThrows(NotFoundException.class, () -> service.findById(anyLong()));
    }

    @Test
    @Disabled
    void noRecipeByIdTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //assertTrue(service.findById(anyLong()));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteById() {
        //no given

        //when
        service.deleteById(anyLong());
        service.deleteById(anyLong());
        service.deleteById(anyLong());

        //then
        verify(repository, times(3)).deleteById(anyLong());
    }
}
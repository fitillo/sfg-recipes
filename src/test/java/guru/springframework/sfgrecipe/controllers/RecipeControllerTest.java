package guru.springframework.sfgrecipe.controllers;

import guru.springframework.sfgrecipe.model.Recipe;
import guru.springframework.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController controller;

    @Mock
    private RecipeService service;

    @Mock
    private Model model;

    @Captor
    private ArgumentCaptor<Recipe> captor;

    final Long id = 1L;
    final Recipe guacamole = Recipe.builder().id(id).description("Guacamole").prepTime(10).cookTime(20).
            servings(5).source("Simply Recipes").build();

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRecipeById() {
        when(service.findById(id)).thenReturn(Optional.of(guacamole));

        assertEquals("recipes/show", controller.showById(id.toString(), model));
        verify(service, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), captor.capture());
        assertEquals(id, captor.getValue().getId());
    }

    @Test
    void noRecipeById() {
        when(service.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> controller.showById(id.toString(), model));
    }

    @Test
    void testMockMVC() throws Exception {
        when(service.findById(id)).thenReturn(Optional.of(guacamole));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}
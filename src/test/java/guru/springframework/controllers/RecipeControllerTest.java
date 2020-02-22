package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    public static final String PAELLA = "Paella";
    public static final Long ID = 1L;
    @InjectMocks
    private RecipeController controller;

    @Mock
    private RecipeService service;

    @Mock
    private Model model;

    @Captor
    private ArgumentCaptor<Recipe> captor;

    final Recipe guacamole = Recipe.builder().id(ID).description("Guacamole").prepTime(10).cookTime(20).
            servings(5).source("Simply Recipes").build();

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRecipeById() {
        when(service.findById(ID)).thenReturn(Optional.of(guacamole));

        assertEquals("recipe/show", controller.showById(ID.toString(), model));
        verify(service, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), captor.capture());
        assertEquals(ID, captor.getValue().getId());
    }

    @Test
    void noRecipeById() {
        when(service.findById(ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> controller.showById(ID.toString(), model));
    }

    @Test
    void testMockMVC() throws Exception {
        when(service.findById(ID)).thenReturn(Optional.of(guacamole));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testNewRecipeMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    @Disabled
    void testNewRecipeAddedMockMVC() throws Exception {
        RecipeCommand detached = RecipeCommand.builder().description(PAELLA).build();
        RecipeCommand saved = RecipeCommand.builder().id(ID).description(PAELLA).build();
        when(service.saveRecipeCommand(any())).thenReturn(saved);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/recipe/"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/recipe/show/"+ID));
    }
}
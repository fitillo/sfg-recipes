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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

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

    MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<Recipe> captor;

    final Recipe guacamole = Recipe.builder().id(ID).description("Guacamole").prepTime(10).cookTime(20).
            servings(5).source("Simply Recipes").build();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getRecipeById() {
        when(service.findById(ID)).thenReturn(guacamole);

        assertEquals("recipe/show", controller.showById(ID.toString(), model));
        verify(service, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), captor.capture());
        assertEquals(ID, captor.getValue().getId());
    }

    @Test
    @Disabled
    void noRecipeById() {
        //when(service.findById(ID)).thenReturn(new RuntimeException());

        assertThrows(RuntimeException.class, () -> controller.showById(ID.toString(), model));
    }

    @Test
    void testMockMVC() throws Exception {
        when(service.findById(ID)).thenReturn(guacamole);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testNewRecipeMockMVC() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testNewRecipeAddedMockMVC() throws Exception {
        RecipeCommand saved = RecipeCommand.builder().id(ID).description(PAELLA).build();
        when(service.saveRecipeCommand(any())).thenReturn(saved);

        mockMvc.perform(post("/recipe/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+ID+"/show"));
    }

    @Test
    void testUpdateRecipe() throws Exception {
        RecipeCommand updated = RecipeCommand.builder().id(ID).description(PAELLA).build();
        when(service.findCommandById(ID)).thenReturn(updated);

        mockMvc.perform(get("/recipe/"+ID+"/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testUpdateNotExistentRecipe() throws Exception {
        when(service.findCommandById(ID)).thenReturn(null);

        mockMvc.perform(get("/recipe/"+ID+"/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/error"));
    }

    @Test
    void testDeleteRecipe() throws Exception {
        mockMvc.perform(get("/recipe/"+ID+"/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(service, times(1)).deleteById(anyLong());
    }
}
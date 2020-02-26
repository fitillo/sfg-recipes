package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    public static final long ID = 1L;
    @InjectMocks
    private IngredientController controller;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ExceptionHandlerController.class).build();
    }

    @Test
    void testListIngredients() throws Exception {
        RecipeCommand recipeCommand = RecipeCommand.builder().id(ID).build();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/ingredient/list"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void testListIngredientsWrongId() throws Exception {
        mockMvc.perform(get("/recipe/text/ingredients"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attribute("exception", Matchers.containsString("java.lang.NumberFormatException: For input string: \"text\"")));
    }

    @Test
    void testShowIngredient() throws Exception {
        //given
        IngredientCommand i1 = IngredientCommand.builder().id(ID).build();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(i1);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    void testShowIngredientWrongId() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/text/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attribute("exception", Matchers.containsString("java.lang.NumberFormatException: For input string: \"text\"")));
    }

    @Test
    void testUpdateRecipeIngredient() throws Exception {
        //given
        IngredientCommand command = IngredientCommand.builder().build();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));

        verify(ingredientService).findByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(unitOfMeasureService).listAllUoms();
    }

    @Test
    void testUpdateIngredientWrongId() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/text/update"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attribute("exception", Matchers.containsString("java.lang.NumberFormatException: For input string: \"text\"")));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = IngredientCommand.builder().id(ID).recipeId(ID).build();

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/1/ingredient/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
            )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+ID+"/ingredient/"+ID+"/show"));
    }

    @Test
    void testAddNewIngredient() throws Exception{
        //given
        IngredientCommand command = IngredientCommand.builder().id(ID).recipeId(ID).build();

        //when
        when(recipeService.findById(anyLong())).thenReturn(Recipe.builder().build());
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));

        verify(recipeService).findById(anyLong());
        verify(unitOfMeasureService).listAllUoms();
    }

    @Test
    void testDeleteRecipeIngredient() throws Exception {
        mockMvc.perform(get("/recipe/"+ID+"/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+ID+"/ingredients"));

        verify(ingredientService).deleteByRecipeIdIngredientId(anyLong(), anyLong());
    }

    @Test
    void testDeleteRecipeIngredientWrongId() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/text/delete"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attribute("exception", Matchers.containsString("java.lang.NumberFormatException: For input string: \"text\"")));
    }
}
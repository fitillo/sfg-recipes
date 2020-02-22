package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String DESCRIPTION = "My new Description";
    @Autowired
    RecipeService service;

    @Autowired
    RecipeToRecipeCommand toRecipeCommand;

    @Autowired
    RecipeCommandToRecipe toRecipe;

    @Test
    @Transactional
    void saveRecipeCommand() {
        RecipeCommand command = toRecipeCommand.convert(service.getRecipes().stream().findFirst().get());

        command.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = service.saveRecipeCommand(command);

        assertEquals(service.findById(command.getId()).get().getDescription(), DESCRIPTION);
        assertEquals(service.findById(savedRecipeCommand.getId()).get().getDescription(), DESCRIPTION);
    }
}

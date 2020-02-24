package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient toIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand toIngredientCommand, IngredientCommandToIngredient toIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.toIngredientCommand = toIngredientCommand;
        this.toIngredient = toIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.orElseThrow(RuntimeException::new);

        Optional<IngredientCommand> command = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(toIngredientCommand::convert).findFirst();

        if (command.isEmpty()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return command.orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (recipeOptional.isEmpty()) {
            //todo error handling
            log.error("Recipe not found for ingredient id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isEmpty()) {
                recipe.addIngredient(toIngredient.convert(command));

                return toIngredientCommand.convert(
                        recipeRepository.save(recipe).getIngredients().stream().findFirst().get());
            } else {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(command.getAmount());
                ingredient.setName(command.getName());
                ingredient.setSuggestion(command.getSuggestion());
                ingredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo error handling
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return toIngredientCommand.convert(savedRecipe.getIngredients()
                    .stream().filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                    .findFirst().get());
        }
    }
}

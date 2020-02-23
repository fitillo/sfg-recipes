package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;
    private final RecipeCommandToRecipe commandToRecipe;
    private final RecipeToRecipeCommand recipeToCommand;

    public RecipeServiceImpl(RecipeRepository repository, RecipeCommandToRecipe commandToRecipe, RecipeToRecipeCommand recipeToCommand) {
        this.repository = repository;
        this.commandToRecipe = commandToRecipe;
        this.recipeToCommand = recipeToCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        if (log.isDebugEnabled()) {
            log.debug("getRecipes services on "+this.getClass().getName()+" class executing");
        }
        Set<Recipe> recipes = new HashSet<>();
        repository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        if (log.isDebugEnabled()) {
            log.debug("getRecipeById services on "+this.getClass().getName()+" class executing");
        }

        return repository.findById(id).orElseThrow(() -> new RuntimeException("No recipe with id "+id+" found"));
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToCommand.convert(this.findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = commandToRecipe.convert(recipeCommand);
        if (detachedRecipe != null) {
            Recipe recipe = this.repository.save(detachedRecipe);
            if (log.isDebugEnabled()) {
                log.debug("Saved RecipeId: " + recipe.getId());
            }
            return recipeToCommand.convert(recipe);
        } else {
            return null;
        }
    }
}

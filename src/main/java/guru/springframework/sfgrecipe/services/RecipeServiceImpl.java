package guru.springframework.sfgrecipe.services;

import guru.springframework.sfgrecipe.model.Recipe;
import guru.springframework.sfgrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        if (log.isDebugEnabled()) {
            log.debug("getRecipes services on "+this.getClass().getName()+" class executing");
        }
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        if (log.isDebugEnabled()) {
            log.debug("getRecipeById services on "+this.getClass().getName()+" class executing");
        }
        return recipeRepository.findById(id);
    }
}

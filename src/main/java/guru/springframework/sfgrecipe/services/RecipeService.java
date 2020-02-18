package guru.springframework.sfgrecipe.services;

import guru.springframework.sfgrecipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}

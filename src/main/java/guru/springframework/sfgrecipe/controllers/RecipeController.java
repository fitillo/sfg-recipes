package guru.springframework.sfgrecipe.controllers;

import guru.springframework.sfgrecipe.model.Recipe;
import guru.springframework.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/recipe/")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @RequestMapping("show/{id}")
    public String showById(@PathVariable String id, Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Request for recipe by id. Controller executing....");
        }

        Optional<Recipe> recipe = service.findById(Long.valueOf(id));
        if (recipe.isEmpty()) {
            throw new RuntimeException("Recipe Not Found!!");
        }
        model.addAttribute("recipe", recipe.get());
        return "recipes/show";
    }
}

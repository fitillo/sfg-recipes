package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "recipe/show";
    }

    @RequestMapping("new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = service.saveRecipeCommand(command);

        return "redirect:/recipe/show/"+savedCommand.getId();
    }
}

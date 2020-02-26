package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/recipe/")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("{id}/show")
    public String showById(@PathVariable String id, Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Request for recipe by id. Controller executing....");
        }

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());


        return "recipe/recipeform";
    }

    @GetMapping("{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(id));

        if (recipe == null) {
            return "redirect:/error";
        }
        model.addAttribute("recipe", recipe);
        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("{id}/delete")
    public String deleteRecipe(@PathVariable String id) {

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found error");
        ModelAndView modelAndView = new ModelAndView("404error");
        modelAndView.addObject("exception", exception.getMessage());
        return modelAndView;
    }
}

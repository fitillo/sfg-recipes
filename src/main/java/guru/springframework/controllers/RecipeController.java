package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe/")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    @RequestMapping("{id}/show")
    public String showById(@PathVariable String id, Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Request for recipe by id. Controller executing....");
        }

        model.addAttribute("recipe", service.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        RecipeCommand recipe = service.findCommandById(Long.valueOf(id));

        if (recipe == null) {
            return "redirect:/error";
        }
        model.addAttribute("recipe", recipe);
        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = service.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
package guru.springframework.sfgrecipe.controllers;

import guru.springframework.sfgrecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/"})
    String getIndexPage(Model model) {
        //model.addAttribute("recipe", categoryRepository.findByName("Italian").get().getName());
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}

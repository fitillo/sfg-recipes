package guru.springframework.sfgrecipe.controllers;

import guru.springframework.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/"})
    String getIndexPage(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Request for index page. Controller executing...");
        }
        //model.addAttribute("recipe", categoryRepository.findByName("Italian").get().getName());
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}

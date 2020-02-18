package guru.springframework.sfgrecipe.bootstrap;

import guru.springframework.sfgrecipe.model.*;
import guru.springframework.sfgrecipe.repositories.CategoryRepository;
import guru.springframework.sfgrecipe.repositories.RecipeRepository;
import guru.springframework.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;

    public DataLoader(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        Ingredient avocados = new Ingredient("Avocado", 2d, "Ripe", unitOfMeasureRepository.findByUnitOfMeasure("Unit").get());
        Ingredient salt = new Ingredient("Salt", 0.25d, "More to taste", unitOfMeasureRepository.findByUnitOfMeasure("Teaspoon").get());

        Category mexican = categoryRepository.findByName("Mexican").get();
        Category fastFood = categoryRepository.findByName("Fast Food").get();

        Recipe guacamole = new Recipe();
        /*avocados.setRecipe(guacamole);
        salt.setRecipe(guacamole);*/
        guacamole.addIngredient(avocados);
        guacamole.addIngredient(salt);

        Note note = new Note();
        note.setNotes("The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");
        //note.setRecipe(guacamole);
        guacamole.setNotes(note);

        guacamole.getCategories().add(mexican);
        guacamole.getCategories().add(fastFood);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDescription("Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(20);
        guacamole.setServings(5);
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        recipeRepository.save(guacamole);

        Ingredient oregano = new Ingredient("Oregano", 1d, "Dried", unitOfMeasureRepository.findByUnitOfMeasure("Teaspoon").get());
        Ingredient chili = new Ingredient("Chili powder", 2d, "Ancho", unitOfMeasureRepository.findByUnitOfMeasure("Tablespoon").get());

        Recipe tacos = new Recipe();
        /*oregano.setRecipe(tacos);
        chili.setRecipe(tacos);*/
        tacos.addIngredient(oregano);
        tacos.addIngredient(chili);

        Note note1 = new Note();
        note1.setNotes("The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");
        //note1.setRecipe(tacos);
        tacos.setNotes(note1);

        tacos.getCategories().add(mexican);
        tacos.getCategories().add(fastFood);
        tacos.setDifficulty(Difficulty.MODERATE);
        tacos.setDescription("Tacos");
        tacos.setPrepTime(30);
        tacos.setCookTime(40);
        tacos.setServings(3);
        tacos.setSource("Simply Recipes");
        tacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        recipeRepository.save(tacos);
    }
}

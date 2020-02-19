package guru.springframework.sfgrecipe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = Category.builder().id(1L).name("Mexican").build();
    }

    @Test
    void getId() {
        assertTrue(category.getId() == 1L);
    }

    @Test
    void getName() {
        assertTrue(category.getName().equals("Mexican"));
    }

    @Test
    void getRecipes() {
        assertTrue(category.getRecipes().isEmpty());
    }
}
package guru.springframework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = Category.builder().id(1L).name("Mexican").build();
    }

    @Test
    void getId() {
        assertEquals(1L, (long) category.getId());
    }

    @Test
    void getName() {
        assertEquals("Mexican", category.getName());
    }

    @Test
    void getRecipes() {
        assertTrue(category.getRecipes().isEmpty());
    }
}
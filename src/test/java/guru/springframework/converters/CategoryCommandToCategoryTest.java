package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    public static final long ID = 1L;
    public static final String NAME = "mexican";
    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Disabled
    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        CategoryCommand command = CategoryCommand.builder().id(ID).name(NAME).build();
        Category category = converter.convert(command);

        assertNotNull(category);
        assertEquals(ID, category.getId());
        assertEquals(NAME, category.getName());
        assertNotNull(category.getRecipes());
    }
}
package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    public static final long ID = 1L;
    public static final String NAME = "mexican";
    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Disabled
    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        Category category = Category.builder().id(ID).name(NAME).build();
        CategoryCommand command = converter.convert(category);

        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(NAME, command.getName());
    }
}
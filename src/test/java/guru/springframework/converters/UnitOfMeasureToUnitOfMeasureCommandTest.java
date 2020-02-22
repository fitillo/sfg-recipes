package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final long ID = 1L;
    public static final String CUP = "cup";
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void testNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmpty() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        UnitOfMeasure command = UnitOfMeasure.builder().id(ID).unitOfMeasure(CUP).build();
        UnitOfMeasureCommand uom = converter.convert(command);

        assertNotNull(uom);
        assertEquals(ID, uom.getId());
        assertEquals(CUP, uom.getUnitOfMeasure());
    }
}
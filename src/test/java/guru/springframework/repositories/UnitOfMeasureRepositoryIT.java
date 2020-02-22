package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    //@DirtiesContext This would make spring to refresh the whole context
    void findByUnitOfMeasure() {
        Optional<UnitOfMeasure> uomOptional = repository.findByUnitOfMeasure("Teaspoon");

        assertTrue(uomOptional.isPresent());
        assertEquals(uomOptional.get().getUnitOfMeasure(), "Teaspoon");
    }

    @Test
    void findByUnitOfMeasureCup() {
        Optional<UnitOfMeasure> uomOptional = repository.findByUnitOfMeasure("Cup");

        assertTrue(uomOptional.isPresent());
        assertEquals(uomOptional.get().getUnitOfMeasure(), "Cup");
    }
}
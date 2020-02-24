package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    public static final long ID = 1L;
    public static final String CUPS = "Cups";
    public static final String TEASPOONS = "Teaspoons";
    @InjectMocks
    private UnitOfMeasureServiceImpl service;

    @Mock
    private UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
        service = new UnitOfMeasureServiceImpl(repository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listEmptyUoms() {
        //given
        Set<UnitOfMeasure> emptySet = new HashSet<>();

        //when
        when(repository.findAll()).thenReturn(emptySet);

        //then
        assertEquals(service.listAllUoms().size(), 0);
    }

    @Test
    void listAllUoms() {
        //given
        Set<UnitOfMeasure> set = Set.of(UnitOfMeasure.builder().id(ID).description(CUPS).build()
                , UnitOfMeasure.builder().id(ID+1).description(TEASPOONS).build());

        //when
        when(repository.findAll()).thenReturn(set);

        //then
        Set<UnitOfMeasureCommand> list = service.listAllUoms();
        assertEquals(list.size(), 2);
        assertEquals(1, list.stream().filter(uom -> uom.getId().equals(ID)).count());
        assertEquals(1, list.stream().filter(uom -> uom.getDescription().equals(TEASPOONS)).count());
        verify(repository, times(1)).findAll();
    }
}
package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        IngredientCommand command = IngredientCommand.builder().id(source.getId()).name(source.getName())
                .amount(source.getAmount()).suggestion(source.getSuggestion())
                .uom(uomConverter.convert(source.getUom())).build();

        if (source.getRecipe() != null) {
            command.setRecipeId(source.getRecipe().getId());
        }

        return command;
    }
}

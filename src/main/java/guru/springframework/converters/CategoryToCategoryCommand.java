package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Override
    public CategoryCommand convert(Category source) {
        return (source == null) ? null :
                CategoryCommand.builder().id(source.getId()).description(source.getDescription()).build();
    }
}

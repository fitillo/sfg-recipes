package guru.springframework.sfgrecipe.repositories;

import guru.springframework.sfgrecipe.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByName(String name);
}

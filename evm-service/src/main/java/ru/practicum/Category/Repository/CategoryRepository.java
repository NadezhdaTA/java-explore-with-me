package ru.practicum.Category.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Category.Model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category save(Category category);

    void deleteById(Integer id);

    Optional<Category> findById(Integer id);


}

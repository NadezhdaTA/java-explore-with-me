package ru.practicum.Category.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.Category.Model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer>, QuerydslPredicateExecutor<Category> {
    Category save(Category category);

    void deleteById(Integer id);

    Optional<Category> findById(Integer id);


}

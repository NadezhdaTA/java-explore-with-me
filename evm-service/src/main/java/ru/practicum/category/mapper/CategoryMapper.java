package ru.practicum.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.category.dto.NewCategoryDTO;
import ru.practicum.category.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(NewCategoryDTO categoryDTO);

    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDTO);
}

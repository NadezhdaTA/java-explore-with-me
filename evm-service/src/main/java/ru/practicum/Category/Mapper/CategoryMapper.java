package ru.practicum.Category.Mapper;

import org.mapstruct.Mapper;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.DTO.NewCategoryDTO;
import ru.practicum.Category.Model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(NewCategoryDTO categoryDTO);

    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDTO);
}

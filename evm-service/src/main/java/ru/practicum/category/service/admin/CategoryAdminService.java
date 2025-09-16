package ru.practicum.category.service.admin;

import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.category.dto.NewCategoryDTO;

public interface CategoryAdminService {
    CategoryDTO addCategory(NewCategoryDTO categoryDTO);

    void deleteCategory(Integer catId);

    CategoryDTO updateCategory(int id, CategoryDTO categoryDTO);
}

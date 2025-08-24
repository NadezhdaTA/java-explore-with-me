package ru.practicum.Category.Service.Admin;

import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.DTO.NewCategoryDTO;

public interface CategoryAdminService {
    CategoryDTO addCategory(NewCategoryDTO categoryDTO);

    void deleteCategory(Integer catId);

    CategoryDTO updateCategory(int id, CategoryDTO categoryDTO);
}

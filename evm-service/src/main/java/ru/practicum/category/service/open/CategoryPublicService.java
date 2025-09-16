package ru.practicum.category.service.open;

import ru.practicum.category.dto.CategoryDTO;

import java.util.List;

public interface CategoryPublicService {
    CategoryDTO getCategory(Integer catId);

    List<CategoryDTO> getCategories(Integer from, Integer size);
}

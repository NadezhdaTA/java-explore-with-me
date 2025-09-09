package ru.practicum.Category.Service.Public;

import ru.practicum.Category.DTO.CategoryDTO;

import java.util.List;

public interface CategoryPublicService {
    CategoryDTO getCategory(Integer catId);

    List<CategoryDTO> getCategories(Integer from, Integer size);
}

package ru.practicum.Category.Service.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.DTO.NewCategoryDTO;
import ru.practicum.Category.Mapper.CategoryMapper;
import ru.practicum.Category.Model.Category;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository repository;

    @Override
    public CategoryDTO addCategory(NewCategoryDTO newCategory) {
        Category category = categoryMapper.toCategory(newCategory);
        return categoryMapper.toCategoryDTO(repository.save(category));
    }

    @Override
    public void deleteCategory(Integer catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));

        // Integer categoryEvents =

        repository.deleteById(catId);
    }

    @Override
    public CategoryDTO updateCategory(int id, CategoryDTO categoryDTO) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        return categoryMapper.toCategoryDTO(repository.save(categoryMapper.toCategory(categoryDTO)));
    }


}

package ru.practicum.Category.Service.Public;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.Mapper.CategoryMapper;
import ru.practicum.Category.Model.Category;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategory(Integer catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));
        return categoryMapper.toCategoryDTO(category);
    }



    @Override
    public List<CategoryDTO> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id").ascending());
        Page<Category> categories = repository.findAll(pageable);
        return categories.getContent().stream()
                .map(categoryMapper::toCategoryDTO)
                .toList();
    }
}

package ru.practicum.category.service.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryPublicServiceImpl implements CategoryPublicService {
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategory(Integer catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));
        CategoryDTO categoryDTO = categoryMapper.toCategoryDTO(category);
        log.info("Category is found: {}", categoryDTO);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id").ascending());
        Page<Category> categories = repository.findAll(pageable);
        log.info("Found categories are sent");
        return categories.getContent().stream()
                .map(categoryMapper::toCategoryDTO)
                .toList();
    }
}

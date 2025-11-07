package ru.practicum.category.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.category.dto.NewCategoryDTO;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDTO addCategory(NewCategoryDTO newCategory) {
        Category category = categoryMapper.toCategory(newCategory);
        CategoryDTO categoryDTO = categoryMapper.toCategoryDTO(repository.save(category));
        log.info("Category is added: {}", categoryDTO);
        return categoryDTO;
    }

    @Override
    public void deleteCategory(Integer catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));

        List<Event> events = eventRepository.findEventsByCategory_Id(catId);
        if (!events.isEmpty()) {
            throw new ConflictException("Category with id " + catId + " has connected events");
        }

        repository.deleteById(catId);
        log.info("Category is deleted: {}", category);
    }

    @Override
    public CategoryDTO updateCategory(int id, CategoryDTO newCategory) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        CategoryDTO categoryDTO;
        if (category.getName().equals(newCategory.getName())) {
            categoryDTO = categoryMapper.toCategoryDTO(category);
        } else {
            categoryDTO = categoryMapper.toCategoryDTO(repository.save(categoryMapper.toCategory(newCategory)));
        }

        log.info("Category is updated: {}", categoryDTO);
        return categoryDTO;
    }


}

package ru.practicum.Category.Service.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.DTO.NewCategoryDTO;
import ru.practicum.Category.Mapper.CategoryMapper;
import ru.practicum.Category.Model.Category;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.ConflictException;
import ru.practicum.Exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository repository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDTO addCategory(NewCategoryDTO newCategory) {
        Category category = categoryMapper.toCategory(newCategory);
        return categoryMapper.toCategoryDTO(repository.save(category));
    }

    @Override
    public void deleteCategory(Integer catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));

        List<Event> events = eventRepository.findEventsByCategory_Id(catId);
        if (!events.isEmpty()) {
            throw new ConflictException("Category with id " + catId + " has connected events");
        }

        // Integer categoryEvents =

        repository.deleteById(catId);
    }

    @Override
    public CategoryDTO updateCategory(int id, CategoryDTO newCategory) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));

        if (category.getName().equals(newCategory.getName())) {
            return categoryMapper.toCategoryDTO(category);
        } else {
            return categoryMapper.toCategoryDTO(repository.save(categoryMapper.toCategory(newCategory)));
        }


    }


}

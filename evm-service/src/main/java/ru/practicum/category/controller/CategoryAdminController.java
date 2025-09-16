package ru.practicum.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.category.dto.NewCategoryDTO;
import ru.practicum.category.service.admin.CategoryAdminService;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {
    private final CategoryAdminService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody @Valid NewCategoryDTO category) {
        log.info("Create new category: {}", category);
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer catId) {
        log.info("Delete category: {}", catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateCategory(@PathVariable Integer catId, @RequestBody @Valid CategoryDTO category) {
        log.info("Update category: {}, {}", catId, category);
        return categoryService.updateCategory(catId, category);
    }

}

package ru.practicum.Category.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.DTO.NewCategoryDTO;
import ru.practicum.Category.Service.Admin.CategoryAdminServiceImpl;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody @Valid NewCategoryDTO category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer catId) {
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateCategory(@PathVariable Integer catId, @RequestBody @Valid CategoryDTO category) {
        return categoryService.updateCategory(catId, category);
    }

}

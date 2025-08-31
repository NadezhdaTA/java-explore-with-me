package ru.practicum.Category.Controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Category.Service.Public.CategoryPublicService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPublicController {
    private final CategoryPublicService categoryService;

    @GetMapping("/{catId}")
    public CategoryDTO getCategory(@PathVariable Integer catId) {
        return categoryService.getCategory(catId);
    }

    @GetMapping
    public List<CategoryDTO> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        return categoryService.getCategories(from, size);
    }
}

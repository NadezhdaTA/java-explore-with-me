package ru.practicum.category.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.category.service.open.CategoryPublicService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryPublicController {
    private final CategoryPublicService categoryService;

    @GetMapping("/{catId}")
    public CategoryDTO getCategory(@PathVariable Integer catId) {
        log.info("Get category: {}", catId);
        return categoryService.getCategory(catId);
    }

    @GetMapping
    public List<CategoryDTO> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Get categories: {}, {}", from, size);
        return categoryService.getCategories(from, size);
    }
}

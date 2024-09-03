package com.dfortch.rarebook.controller;

import com.dfortch.rarebook.dto.entity.CategoryDTO;
import com.dfortch.rarebook.dto.request.CreateCategoryRequest;
import com.dfortch.rarebook.dto.request.GetCategoriesFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateCategoryRequest;
import com.dfortch.rarebook.dto.response.MessageResponse;
import com.dfortch.rarebook.dto.response.RecordCreatedResponse;
import com.dfortch.rarebook.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.dfortch.rarebook.RareBookApiApplicationConstants.API_VERSION;

@RestController
@RequestMapping("/api/" + API_VERSION + "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryDTO> getCategories(@Valid GetCategoriesFilterOptions filterOptions, Pageable pageable) {
        return categoryService.getCategories(pageable, filterOptions);
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecordCreatedResponse createCategory(@Valid @RequestBody CreateCategoryRequest requestDto) {
        Long categoryId = categoryService.createCategory(requestDto);
        return new RecordCreatedResponse("Category created successfully", categoryId);
    }

    @PutMapping("/{id}")
    public MessageResponse updateCategory(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest requestDto) {
        categoryService.updateCategory(id, requestDto);
        return new MessageResponse("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new MessageResponse("Category deleted successfully");
    }
}

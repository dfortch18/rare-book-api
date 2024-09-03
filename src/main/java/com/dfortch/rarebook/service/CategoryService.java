package com.dfortch.rarebook.service;

import com.dfortch.rarebook.dto.entity.CategoryDTO;
import com.dfortch.rarebook.dto.request.CreateCategoryRequest;
import com.dfortch.rarebook.dto.request.GetCategoriesFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryDTO> getCategories(Pageable pageable, GetCategoriesFilterOptions filterOptions);

    CategoryDTO getCategoryById(Long id);

    CategoryDTO getCategoryBySlug(String slug);

    Long createCategory(CreateCategoryRequest requestDto);

    void updateCategory(Long id, UpdateCategoryRequest requestDto);

    void deleteCategory(Long id);
}

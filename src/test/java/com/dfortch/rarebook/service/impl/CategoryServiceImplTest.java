package com.dfortch.rarebook.service.impl;

import com.dfortch.rarebook.common.exception.RecordConflictException;
import com.dfortch.rarebook.common.exception.RecordNotFoundException;
import com.dfortch.rarebook.dto.entity.CategoryDTO;
import com.dfortch.rarebook.dto.request.CreateCategoryRequest;
import com.dfortch.rarebook.dto.request.GetCategoriesFilterOptions;
import com.dfortch.rarebook.dto.request.UpdateCategoryRequest;
import com.dfortch.rarebook.mapper.CategoryMapper;
import com.dfortch.rarebook.persistence.entity.Category;
import com.dfortch.rarebook.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Fiction")
                .slug("fiction")
                .description("A genre of speculative fiction")
                .build();

        categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Fiction")
                .slug("fiction")
                .build();
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetCategories_Success() {
        Page<Category> categoryPage = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll((Specification<Category>) any(), any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.mapToDTO(any(Category.class))).thenReturn(categoryDTO);

        Page<CategoryDTO> result = categoryService.getCategories(Pageable.unpaged(),
                new GetCategoriesFilterOptions(null));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Fiction", result.getContent().getFirst().getName());
    }

    @Test
    void testGetCategoryById_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.mapToDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Fiction", result.getName());
    }

    @Test
    void testGetCategoryBySlug_Success() {
        when(categoryRepository.findBySlug(anyString())).thenReturn(Optional.of(category));
        when(categoryMapper.mapToDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getCategoryBySlug("fiction");

        assertNotNull(result);
        assertEquals("fiction", result.getSlug());
    }

    @Test
    void testGetCategoryBySlug_NotFound() {
        when(categoryRepository.findBySlug(anyString())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> categoryService.getCategoryBySlug("fiction"));
    }

    @Test
    void testCreateCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest("Science Fiction", "science-fiction", "Books about science fiction");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Long categoryId = categoryService.createCategory(request);

        assertNotNull(categoryId);
        assertEquals(1L, categoryId);
    }

    @Test
    void testUpdateCategory_Success() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("Updated Fiction", "updated-fiction", "Updated description");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.existsBySlugAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.updateCategory(1L, request);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_SlugAlreadyUsed() {
        UpdateCategoryRequest request = new UpdateCategoryRequest("Updated Fiction", "fiction", "Updated description");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.existsBySlugAndIdNot(anyString(), anyLong())).thenReturn(true);

        assertThrows(RecordConflictException.class, () -> categoryService.updateCategory(1L, request));
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.existsById(anyLong())).thenReturn(true);

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}
